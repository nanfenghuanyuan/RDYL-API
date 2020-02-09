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
import com.zh.module.service.BindInfoService;
import com.zh.module.service.DailyCountService;
import com.zh.module.service.PetsMatchingListService;
import com.zh.module.service.SysparamsService;
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
public class DailyCountBizImpl implements DailyCountBiz {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private DailyCountService dailyCountService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public void consume() {
        String date = DateUtils.getSomeDay1(-1);
        String start = date + " 00:00:00";
        String end = date + " 23:59:59";
        String todayAmount = petsMatchingListService.consumeTodayAmount(start, end);
        Map<Object, Object> map = new HashMap<>();
        map.put("firstResult", 0);
        map.put("maxResult", 1);
        List<DailyCount> dailyCounts = dailyCountService.selectPaging(map);
        DailyCount dailyCount = new DailyCount();
        dailyCount.setCoinType((byte) CoinType.OS);
        dailyCount.setDailtConsume(StrUtils.isBlank(todayAmount) ? "0" : todayAmount);
        if(dailyCounts != null && dailyCounts.size() != 0){
            DailyCount dailyCount1 = dailyCounts.get(0);
            if(StrUtils.isBlank(dailyCount1.getTotalConsume())){
                dailyCount1.setTotalConsume("0");
            }
            dailyCount.setTotalConsume(new BigDecimal(dailyCount1.getTotalConsume()).add(new BigDecimal(todayAmount)).toEngineeringString());
        }
        dailyCountService.insertSelective(dailyCount);
    }
}