package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.biz.BindInfoBiz;
import com.zh.module.biz.DailyCountBiz;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.encrypt.MD5;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.service.*;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.RedisUtil;
import com.zh.module.utils.StrUtils;
import com.zh.module.variables.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
@Transactional
public class DailyCountBizImpl implements DailyCountBiz {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private DailyCountService dailyCountService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private PetsCountService petsCountService;
    @Autowired
    private IdcardValidateService idcardValidateService;
    @Autowired
    private ProfitRecordService profitRecordService;

    @Override
    public void consume() {
        String date = DateUtils.getSomeDay1(-1);
        String start = date + " 00:00:00";
        String end = date + " 23:59:59";
        //当日消耗数量
        String todayAmount = petsMatchingListService.consumeTodayAmount(start, end);
        todayAmount = StrUtils.isBlank(todayAmount) ? "0" : todayAmount;
        Map<Object, Object> map = new HashMap<>();
        map.put("firstResult", 0);
        map.put("maxResult", 1);
        List<DailyCount> dailyCounts = dailyCountService.selectPaging(map);
        DailyCount dailyCount = new DailyCount();
        dailyCount.setCoinType((byte) CoinType.OS);
        dailyCount.setDailtConsume(todayAmount);
        if(dailyCounts != null && dailyCounts.size() != 0){
            DailyCount dailyCount1 = dailyCounts.get(0);
            if(StrUtils.isBlank(dailyCount1.getTotalConsume())){
                dailyCount1.setTotalConsume("0");
            }
            dailyCount.setTotalConsume(new BigDecimal(dailyCount1.getTotalConsume()).add(new BigDecimal(todayAmount)).toEngineeringString());
        }
        //当日注册人数
        int registerUser = usersService.selectCountByTime(start, end);
        dailyCount.setRegisterNumber(String.valueOf(registerUser));
        //当日实名人数
        int realNameNumber = idcardValidateService.selectCountByTime(start, end);
        dailyCount.setRealNameNumber(String.valueOf(realNameNumber));
        //当日动态收益
        String profitCount = profitRecordService.selectCountByTime(start, end);
        dailyCount.setDynamicRevenue(profitCount);
        dailyCountService.insertSelective(dailyCount);
    }

    @Override
    public void getPetsCount() {
        Map<Object, Object> param = new HashMap<>();
        PetsCount petsCount;
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
        for (Integer level :list){
            param.put("level", level);
            param.put("state", GlobalParams.PET_LIST_STATE_PROFITING);
            //收益中数量
            int profitCount = petsListService.selectCount(param);
            param.put("state", GlobalParams.PET_LIST_STATE_WAIT);
            //待转让数量
            int waitCount = petsListService.selectCount(param);
            petsCount = new PetsCount();
            petsCount.setHoldNumber(profitCount);
            petsCount.setWaitNumber(waitCount);
            petsCount.setLevel(level.byteValue());
            petsCountService.insertSelective(petsCount);
        }
    }
}