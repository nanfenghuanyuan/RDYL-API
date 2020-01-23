package com.zh.module.biz.impl;

import com.zh.module.biz.PetsBiz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.exception.BanlanceNotEnoughException;
import com.zh.module.model.PageModel;
import com.zh.module.model.PetsMatchingListModel;
import com.zh.module.service.*;
import com.zh.module.utils.*;
import com.zh.module.variables.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
@Transactional
public class PetsBizImpl extends BaseBizImpl implements PetsBiz {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AppointmentRecordService appointmentRecordService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String appointment(Users users, Integer level) throws BanlanceNotEnoughException, ParseException {
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        Pets pets = petsService.selectByLevel(level);
        Integer userId = users.getId();
        //是否在允许的时间范围内
        if(!checkIsDateLimit(level, false)){
            return Result.toResult(ResultCode.TIME_ERROR);
        }
        List<BindInfo> bindInfos = bindInfoService.queryByUser(userId);
        if(bindInfos.size() == 0){
            return Result.toResult(ResultCode.BIND_INFO_NONE);
        }
        //验证是否已存在预约记录
        int count = checkMatchingRecord(userId, level, GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
        if(count != 0){
            return Result.toResult(ResultCode.MATCHING_IS_ALIVE);
        }
        //预约消耗金币数量
        BigDecimal appointmentAmount = pets.getAppointmentAmount();
        //保存预约对象
        PetsMatchingList petsMatchingList = new PetsMatchingList();
        petsMatchingList.setAmount(appointmentAmount);
        petsMatchingList.setBuyUserId(userId);
        petsMatchingList.setLevel(level.byteValue());
        petsMatchingList.setPetListId((byte) GlobalParams.DEFAULT_PETS_ID);
        petsMatchingList.setSaleUserId(GlobalParams.DEFAULT_PETS_ID);
        petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
        petsMatchingList.setAppointmentStartTime(DateUtils.getCurrentTimeStr());
        /*设置失效时间*/
        int interval = 10;
        Sysparams param1 = sysparamsService.getValByKey(SystemParams.PETS_MATCHING_CANCEL_TIME);
        if(param1 != null){
            interval = Integer.parseInt(param1.getKeyval());
        }
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MINUTE, interval);
        petsMatchingList.setAppointmentEndTime(DateUtils.getDateFormate(new Timestamp(current.getTimeInMillis())));
        petsMatchingList.setInactiveTime(new Timestamp(current.getTimeInMillis()));
        petsMatchingListService.insertSelective(petsMatchingList);
        //修改账户信息
        accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, BigDecimalUtils.plusMinus(appointmentAmount), BigDecimal.ZERO, userId, "预约消耗", petsMatchingList.getId());

        //保存预约记录
        AppointmentRecord appointmentRecord = new AppointmentRecord();
        appointmentRecord.setName("预约" + pets.getName());
        appointmentRecord.setSpend(appointmentAmount);
        appointmentRecord.setUserId(userId);
        appointmentRecordService.insertSelective(appointmentRecord);

        String redisKey = String.format(RedisKey.BUY_APPOINTMENT_USER, level, userId);
        RedisUtil.addString(redis, redisKey, "-1");
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String buy(Users users, Integer level) throws ParseException {
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        Pets pets = petsService.selectByLevel(level);
        Integer userId = users.getId();
        //是否在允许的时间范围内
        if(!checkIsDateLimit(level, true)){
            return Result.toResult(ResultCode.TIME_ERROR);
        }
        List<BindInfo> bindInfos = bindInfoService.queryByUser(userId);
        if(bindInfos.size() == 0){
            return Result.toResult(ResultCode.BIND_INFO_NONE);
        }

        Map<Object, Object> param = new HashMap<>();
        param.put("level", level);
        param.put("state", GlobalParams.PET_LIST_STATE_WAIT);
        List<PetsList> petsLists = petsListService.selectAll(param);
        //若没有待转让的宠物 返回失败
        if(petsLists.size() == 0){
            Map<Object, Object> params = new HashMap<>();
            params.put("level", level);
            params.put("state", GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
            List<PetsMatchingList> petsMatchingLists = petsMatchingListService.selectAll(params);
            if(petsMatchingLists.size() != 0) {
                PetsMatchingList petsMatchingList = petsMatchingLists.get(0);
                petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_CANCEL);
                petsMatchingListService.updateByPrimaryKeySelective(petsMatchingList);
                BigDecimal amount = petsMatchingList.getAmount();
                accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, amount, BigDecimal.ZERO, userId, "预约取消返还", petsMatchingList.getId());
                //删除redis预约记录
                String redisKey = String.format(RedisKey.BUY_APPOINTMENT_USER, petsMatchingList.getLevel(), userId);
                RedisUtil.deleteKey(redis, redisKey);
            }
            return Result.toResult(ResultCode.PETS_HAS_NONE);
        }else {
            PetsList petsList = new PetsList();
            //修改宠物信息状态
            for (PetsList list : petsLists) {
                if (!list.getUserId().equals(userId)) {
                    petsList = list;
                    break;
                }
            }
            //自己不能和自己匹配
            if (petsList.getUserId() == null || petsList.getUserId().equals(userId)) {
                return Result.toResult(ResultCode.PETS_HAS_NONE);
            }
            Integer saleUserId = petsList.getUserId();
            petsList.setTransferUserId(userId);
            petsList.setState((byte) GlobalParams.PET_LIST_STATE_WAITING);
            petsListService.updateByPrimaryKey(petsList);

            //验证是否已存在预约记录
            int count = checkMatchingRecord(userId, level, GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
            BigDecimal appointmentAmount;


            /*设置失效时间*/
            int interval = 10;
            Sysparams param1 = sysparamsService.getValByKey(SystemParams.PETS_MATCHING_NO_CONFIRM_CANCEL_TIME);
            if(param1 != null){
                interval = Integer.parseInt(param1.getKeyval());
            }
            Calendar current = Calendar.getInstance();
            current.add(Calendar.MINUTE, interval);
            Date inactiveTime = new Timestamp(current.getTimeInMillis());

            //没有预约
            if (count == 0) {
                appointmentAmount = pets.getPayAmount();
                PetsMatchingList petsMatchingList = new PetsMatchingList();
                petsMatchingList.setLevel(level.byteValue());
                petsMatchingList.setAmount(appointmentAmount);
                petsMatchingList.setBuyUserId(userId);
                petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_NOPAY);
                petsMatchingList.setPetListId(petsList.getId().byteValue());
                petsMatchingList.setSaleUserId(saleUserId);
                petsMatchingList.setInactiveTime(inactiveTime);
                petsMatchingList.setAppointmentStartTime(DateUtils.getCurrentTimeStr());
                petsMatchingList.setAppointmentEndTime(DateUtils.getDateFormate(inactiveTime));
                petsMatchingListService.insertSelective(petsMatchingList);

                accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, BigDecimalUtils.plusMinus(appointmentAmount), BigDecimal.ZERO, userId, "领养消耗", petsMatchingList.getId());
            } else {
                param = new HashMap<>();
                param.put("level", level);
                param.put("petListId", "-1");
                param.put("buyUserId", userId);
                param.put("state", GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
                List<PetsMatchingList> petsMatchingLists = petsMatchingListService.selectAll(param);
                PetsMatchingList petsMatchingList = petsMatchingLists.size() == 0 ? null : petsMatchingLists.get(0);
                if (petsMatchingList != null) {
                    petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_NOPAY);
                    petsMatchingList.setPetListId(petsList.getId().byteValue());
                    petsMatchingList.setSaleUserId(saleUserId);
                    petsMatchingList.setInactiveTime(inactiveTime);
                    petsMatchingList.setAppointmentStartTime(DateUtils.getCurrentTimeStr());
                    petsMatchingList.setAppointmentEndTime(DateUtils.getDateFormate(inactiveTime));
                    petsMatchingListService.updateByPrimaryKeySelective(petsMatchingList);
                }
            }

            //删除redis预约记录
            String redisKey = String.format(RedisKey.BUY_APPOINTMENT_USER, level, userId);
            RedisUtil.deleteKey(redis, redisKey);
            return Result.toResult(ResultCode.SUCCESS);
        }
    }

    /**
     * 检查是否在时间允许的范围内 true：时间允许 false：不在范围内
     * @param level
     * @return
     */
    public boolean checkIsDateLimit(Integer level, boolean isBuy) throws ParseException {
        if(isBuy) {
            Pets pets = petsService.selectByLevel(level);
            String startTime = pets.getStartTime();
            String endTime = pets.getEndTime();
            String today = DateUtils.getCurrentTimeStr();
            startTime = new StringBuilder(today).replace(11, 16, startTime).toString();
            endTime = new StringBuilder(today).replace(11, 16, endTime).toString();
            return DateUtils.minBetween(startTime) >= 0 && DateUtils.minBetween(endTime) <= 0;
        }else{
            String appoinmentTime = sysparamsService.getValStringByKey(SystemParams.APPOINTMENT_TIME);
            int time = Integer.parseInt(appoinmentTime);
            Pets pets = petsService.selectByLevel(level);
            String startTime = pets.getStartTime();
            String endTime = pets.getEndTime();
            String today = DateUtils.getCurrentTimeStr();
            startTime = new StringBuilder(today).replace(11, 16, startTime).toString();
            startTime = DateUtils.dateAddTime(startTime, -time * 60 * 1000);
            endTime = new StringBuilder(today).replace(11, 16, endTime).toString();
            return DateUtils.minBetween(startTime) >= 0 && DateUtils.minBetween(endTime) <= 0;
        }
    }

    /**
     * 统计预约记录表数据数量
     * @param userId
     * @param level
     * @param state
     * @return
     */
    public int checkMatchingRecord(Integer userId, Integer level, Integer state){
        Map<Object, Object> map = new HashMap<>();
        map.put("level", level);
        map.put("buyUserId", userId);
        map.put("state", state);
        return petsMatchingListService.selectCount(map);
    }

    @Override
    public String list(Users users, PageModel pageModel) {
        Map<Object, Object> param = new HashMap<>();
        param.put("userId", users.getId());
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<PetsList> petsLists = petsListService.selectPaging(param);
        List<PetsMatchingListModel> listModels = new LinkedList<>();
        for(PetsList petsList : petsLists){
            Pets pets = petsService.selectByLevel(petsList.getLevel().intValue());
            PetsMatchingListModel petsMatchingListModel = new PetsMatchingListModel();
            petsMatchingListModel.setName(pets.getName());
            petsMatchingListModel.setImgUrl(pets.getImgUrl());
            petsMatchingListModel.setPrice(petsList.getPrice());
            petsMatchingListModel.setProfit(pets.getProfitDays() + "天/" + pets.getProfitRate().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
            petsMatchingListModel.setProfited(petsList.getPrice().multiply(petsList.getProfitRate()));
            petsMatchingListModel.setAppointmentTime(petsList.getStartTime());
            listModels.add(petsMatchingListModel);
        }
        return Result.toResult(ResultCode.SUCCESS, listModels);
    }
}