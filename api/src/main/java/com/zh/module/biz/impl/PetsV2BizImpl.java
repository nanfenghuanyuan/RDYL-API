package com.zh.module.biz.impl;

import com.zh.module.async.BuysAsync;
import com.zh.module.biz.PetsV2Biz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.service.*;
import com.zh.module.utils.BigDecimalUtils;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.RedisUtil;
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
public class PetsV2BizImpl extends BaseBizImpl implements PetsV2Biz {
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
    private BuysAsync buysAsync;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public synchronized String buy(Users users, Integer level) throws ParseException {
        String redisKey;
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        Pets pets = petsService.selectByLevel(level);
        String startTime = new StringBuilder(DateUtils.getCurrentTimeStr()).replace(11, 16, pets.getStartTime()).replace(17, 19,"00").toString();
        int second = DateUtils.secondBetween(startTime);
        if(second > 90){
            return Result.toResult(ResultCode.PETS_HAS_NONE);
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

        //是否在允许的时间范围内
        if(!checkIsDateLimit(level, true)){
            return Result.toResult(ResultCode.TIME_ERROR);
        }
        //查看绑定信息
        List<BindInfo> bindInfos = bindInfoService.queryByUser(userId);
        if(bindInfos.size() == 0){
            return Result.toResult(ResultCode.BIND_INFO_NONE);
        }

        redisKey = String.format(RedisKey.PETS_LIST_BUY_LIST, level);
        List<Integer> list = RedisUtil.searchStringObj(redis, redisKey, List.class);
        list = list == null ? new LinkedList<>() : list;
        list.add(userId);
        RedisUtil.deleteKey(redis, redisKey);
        RedisUtil.addStringObj(redis, redisKey, list);

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public void matching(Integer level) {
        String redisKey = String.format(RedisKey.PETS_LIST_WAIT_APPOINTMENT, level);
        long size = RedisUtil.searchListSize(redis, redisKey);
        String redisKeys = String.format(RedisKey.PETS_LIST_BUY_LIST, level);
        List<Integer> list = RedisUtil.searchStringObj(redis, redisKeys, List.class);
        if(list == null){
            return;
        }
        Pets pets = petsService.selectByLevel(level);
        for (int i = 0; i < size; i++) {
            PetsList petsList = RedisUtil.searchIndexList(redis, redisKey, i, PetsList.class);
            Integer userId = list.get(i);
            if(userId != null && petsList != null){
                Integer saleUserId = petsList.getUserId();

                //验证是否已存在预约记录
                int count = checkMatchingRecord(userId, level);
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

                petsList.setTransferUserId(userId);
                petsList.setState((byte) GlobalParams.PET_LIST_STATE_WAITING);
                petsListService.updateByPrimaryKey(petsList);

                //没有预约
                if (count == 0) {
                    appointmentAmount = pets.getPayAmount();
                    PetsMatchingList petsMatchingList = new PetsMatchingList();
                    petsMatchingList.setLevel(petsList.getLevel());
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
                    Map<Object, Object> param = new HashMap<>();
                    param.put("level", petsList.getLevel());
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
                redisKey = String.format(RedisKey.BUY_APPOINTMENT_USER, level, userId);
                RedisUtil.deleteKey(redis, redisKey);
            }
        }

    }
}