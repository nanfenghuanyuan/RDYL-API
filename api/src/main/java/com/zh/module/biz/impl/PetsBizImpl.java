package com.zh.module.biz.impl;

import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.PetsBiz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.exception.BanlanceNotEnoughException;
import com.zh.module.model.PetsModel;
import com.zh.module.service.*;
import com.zh.module.utils.BigDecimalUtils;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.RedisUtil;
import com.zh.module.utils.StrUtils;
import com.zh.module.variables.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
@Transactional
public class PetsBizImpl implements PetsBiz {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String appointment(Users users, Integer level) throws BanlanceNotEnoughException {
        Pets pets = petsService.selectByLevel(level);
        Integer userId = users.getId();
        //是否在允许的时间范围内
        if(!checkIsDateLimit(level)){
            return Result.toResult(ResultCode.TIME_ERROR);
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
        petsMatchingListService.insertSelective(petsMatchingList);
        //修改账户信息
        accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, BigDecimalUtils.plusMinus(appointmentAmount), appointmentAmount, userId, "预约消耗", petsMatchingList.getId());

        String redisKey = String.format(RedisKey.BUY_APPOINTMENT_USER, level, userId);
        RedisUtil.addString(redis, redisKey, "-1");
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String buy(Users users, Integer level) {
        Pets pets = petsService.selectByLevel(level);
        Integer userId = users.getId();
        //是否在允许的时间范围内
        if(!checkIsDateLimit(level)){
            return Result.toResult(ResultCode.TIME_ERROR);
        }

        Map<Object, Object> param = new HashMap<>();
        param.put("level", level);
        param.put("state", GlobalParams.PET_LIST_STATE_WAIT);
        List<PetsList> petsLists = petsListService.selectAll(param);
        //若没有待转让的宠物 返回失败
        if(petsLists.size() == 0){
            return Result.toResult(ResultCode.PETS_HAS_NONE);
        }
        PetsList petsList = new PetsList();
        //修改宠物信息状态
        for(PetsList list : petsLists){
            if (!list.getUserId().equals(userId)) {
                petsList = list;
                break;
            }
        }
        //自己不能和自己匹配
        if(petsList.getUserId() == null || petsList.getUserId().equals(userId)){
            return Result.toResult(ResultCode.PETS_HAS_NONE);
        }
        Integer saleUserId = petsList.getUserId();
        petsList.setTransferUserId(userId);
        petsList.setState((byte) GlobalParams.PET_LIST_STATE_WAITING);
        petsListService.updateByPrimaryKey(petsList);

        //验证是否已存在预约记录
        int count = checkMatchingRecord(userId, level, GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
        BigDecimal appointmentAmount;
        //没有预约
        if(count == 0){
            appointmentAmount = pets.getPayAmount();
            PetsMatchingList petsMatchingList = new PetsMatchingList();
            petsMatchingList.setLevel(level.byteValue());
            petsMatchingList.setAmount(appointmentAmount);
            petsMatchingList.setBuyUserId(userId);
            petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_NOPAY);
            petsMatchingList.setPetListId(petsList.getId().byteValue());
            petsMatchingList.setSaleUserId(saleUserId);
            petsMatchingListService.insertSelective(petsMatchingList);
        }else{
            param = new HashMap<>();
            param.put("level", level);
            param.put("petListId", "-1");
            param.put("buyUserId", userId);
            param.put("state", GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
            List<PetsMatchingList> petsMatchingLists = petsMatchingListService.selectAll(param);
            PetsMatchingList petsMatchingList = petsMatchingLists.size() == 0 ? null : petsMatchingLists.get(0);
            if(petsMatchingList != null) {
                petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_NOPAY);
                petsMatchingList.setPetListId(petsList.getId().byteValue());
                petsMatchingList.setSaleUserId(saleUserId);
                petsMatchingListService.updateByPrimaryKeySelective(petsMatchingList);
            }
        }

        //删除redis预约记录
        String redisKey = String.format(RedisKey.BUY_APPOINTMENT_USER, level, userId);
        RedisUtil.deleteKey(redis, redisKey);
        return Result.toResult(ResultCode.SUCCESS);
    }

    /**
     * 检查是否在时间允许的范围内 true：时间允许 false：不在范围内
     * @param level
     * @return
     */
    public boolean checkIsDateLimit(Integer level){
        Pets pets = petsService.selectByLevel(level);
        String startTime = pets.getStartTime();
        String endTime = pets.getEndTime();
        String today = DateUtils.getCurrentTimeStr();
        startTime = new StringBuilder(today).replace(11, 16, startTime).toString();
        endTime = new StringBuilder(today).replace(11, 16, endTime).toString();
        return DateUtils.minBetween(startTime) >= 0 && DateUtils.minBetween(endTime) <= 0;
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
}