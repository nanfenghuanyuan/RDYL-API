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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    private BlackListService blackListService;
    @Autowired
    private RedisTemplate<String,String> redis;

    private static final List<Integer> timeList = new ArrayList<>(Arrays.asList(0, 28, 29, 30));

    @Override
    public String appointment(Users users, Integer level) throws BanlanceNotEnoughException, ParseException {
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        /*先完成备用手机的绑定*/
        Map<Object, Object> param = new HashMap<>();
        param.put("userId", users.getId());
        param.put("type", GlobalParams.PAY_PHONE);
        BindInfo buyInfo = bindInfoService.selectByUserAndType(param);
        if(buyInfo == null){
            return Result.toResult(ResultCode.BIND_PHONE_MUST);
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
        petsMatchingList.setPetListId(GlobalParams.DEFAULT_PETS_ID);
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
        current.setTime(DateUtils.strToDate(new StringBuilder(DateUtils.getCurrentTimeStr()).replace(11, 16, pets.getStartTime()).replace(17, 19,"00").toString()));
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
        String redisKey;
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        Integer userId = users.getId();
        //判断是否在黑名单
        BlackList blackList = blackListService.selectByUserId(userId);
        if(blackList != null){
            return Result.toResult(ResultCode.PETS_HAS_NONE);
        }
        /*先完成备用手机的绑定*/
        Map<Object, Object> param = new HashMap<>();
        param.put("userId", users.getId());
        param.put("type", GlobalParams.PAY_PHONE);
        BindInfo buyInfo = bindInfoService.selectByUserAndType(param);
        if(buyInfo == null){
            return Result.toResult(ResultCode.BIND_PHONE_MUST);
        }

        //如果是20秒整购买 直接返回失败（定时任务冲突)
        String today = DateUtils.getCurrentTimeStr();
        String times = new StringBuilder(today).replace(17, 19,  "00").toString();
        /*if(DateUtils.secondBetween(times) % 30 == 0){
            return Result.toResult(ResultCode.PETS_HAS_NONE);
        }*/
        if(timeList.contains(DateUtils.secondBetween(times))){
            return Result.toResult(ResultCode.PETS_HAS_NONE);
        }


        //每个人只允许持有一个
        Map<Object, Object> params = new HashMap<>();
        params.put("buyUserId", userId);
        params.put("level", level);
        params.put("state", GlobalParams.PET_MATCHING_STATE_NOPAY);
        int counts = petsMatchingListService.selectCount(params);
        params.put("state", GlobalParams.PET_MATCHING_STATE_PAYED);
        counts = counts + petsMatchingListService.selectCount(params);
        if(counts != 0){
            return Result.toResult(ResultCode.PERSON_HAS_PETS);
        }

        Pets pets = petsService.selectByLevel(level);
        //是否在允许的时间范围内
        if(!checkIsDateLimit(level, true)){
            return Result.toResult(ResultCode.TIME_ERROR);
        }
        List<BindInfo> bindInfos = bindInfoService.queryByUser(userId);
        if(bindInfos.size() == 0){
            return Result.toResult(ResultCode.BIND_INFO_NONE);
        }
        /*
        //保留购买时间
        String buysInterval = sysparamsService.getValStringByKey(SystemParams.PETS_BUYS_DISTRIBUTE_TIME);

        int second = DateUtils.secondBetween(DateUtils.getCurrentDateStr() + " " + pets.getStartTime() + ":00");
        String flag = "";
        //当处于间隔购买时间时
        if(second < Integer.parseInt(buysInterval)){
            //是否可购买 为空可买
            redisKey = String.format(RedisKey.PETS_LIST_BUY_FLAG, pets.getLevel());
            flag = RedisUtil.searchString(redis, redisKey);
        }
        if(!StrUtils.isBlank(flag)){
            return Result.toResult(ResultCode.PETS_HAS_NONE);
        }*/

        //购买库存列表
        redisKey = String.format(RedisKey.PETS_LIST_WAIT_APPOINTMENT, pets.getLevel());
        PetsList petsList = RedisUtil.leftPopObj(redis, redisKey, PetsList.class);
        if(petsList == null){
            return Result.toResult(ResultCode.PETS_HAS_NONE);
        }else {
            //自己不能和自己匹配
            if (petsList.getUserId() == null || petsList.getUserId().equals(userId)) {
                RedisUtil.addListRight(redis, redisKey, petsList);
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
            Sysparams param1 = sysparamsService.getValByKey(SystemParams.PETS_MATCHING_NO_PAY_CANCEL_TIME);
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
                petsMatchingList.setPetListId(petsList.getId());
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
                PetsMatchingList petsMatchingList =  petsMatchingLists == null || petsMatchingLists.size() == 0 ? null : petsMatchingLists.get(0);
                if (petsMatchingList != null) {
                    petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_NOPAY);
                    petsMatchingList.setPetListId(petsList.getId());
                    petsMatchingList.setSaleUserId(saleUserId);
                    petsMatchingList.setInactiveTime(inactiveTime);
                    petsMatchingList.setAppointmentStartTime(DateUtils.getCurrentTimeStr());
                    petsMatchingList.setAppointmentEndTime(DateUtils.getDateFormate(inactiveTime));
                    petsMatchingListService.updateByPrimaryKeySelective(petsMatchingList);
                }
            }

            //删除redis预约记录
            /*redisKey = String.format(RedisKey.BUY_APPOINTMENT_USER, level, userId);
            RedisUtil.deleteKey(redis, redisKey);

            //总时间
            int seconds = Integer.parseInt(buysInterval);
            redisKey = String.format(RedisKey.PETS_LIST_WAIT_APPOINTMENT_AMOUNT, pets.getLevel());
            //总数量
            String size = RedisUtil.searchString(redis, redisKey);
            //单词分得时间间隔
            long time = seconds/Integer.parseInt(size);

            //已购买标记
            redisKey = String.format(RedisKey.PETS_LIST_BUY_FLAG, pets.getLevel());
            RedisUtil.addString(redis, redisKey, time, "-1");*/
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
            return DateUtils.secondBetween(startTime) >= 0 && DateUtils.secondBetween(endTime) < 0;
        }else{
            Pets pets = petsService.selectByLevel(level);
            String endTime;
            String today = DateUtils.getCurrentTimeStr();
            endTime = new StringBuilder(today).replace(11, 16, pets.getStartTime()).toString();
            return DateUtils.secondBetween(endTime) < 0;
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
            petsMatchingListModel.setNumber(petsList.getPetsNumber());
            petsMatchingListModel.setProfit(pets.getProfitDays() + "天/" + pets.getProfitRate().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
            petsMatchingListModel.setProfited(petsList.getPrice().multiply(petsList.getProfitRate()));
            petsMatchingListModel.setAppointmentTime(petsList.getStartTime());
            listModels.add(petsMatchingListModel);
        }
        return Result.toResult(ResultCode.SUCCESS, listModels);
    }
}