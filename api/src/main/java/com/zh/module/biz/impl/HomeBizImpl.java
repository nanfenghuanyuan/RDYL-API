package com.zh.module.biz.impl;

import com.zh.module.biz.HomeBiz;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PetsModel;
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
public class HomeBizImpl implements HomeBiz {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String init(Users users) {
        Map<Object, Object> result = new HashMap<>();
        Map<Object, Object> param = new HashMap<>();
        param.put("state", GlobalParams.ACTIVE);
        List<Banner> bannerList = bannerService.selectAll(param);
        List<Map<String, Object>> bannersList = new LinkedList<>();
        Map<String, Object> banners = new HashMap<>();
        for(Banner banner : bannerList){
            banners.put("url", banner.getImgpath());
            bannersList.add(banners);
        }
        result.put("banner", bannersList);
        List<Pets> petsList = petsService.selectAll(new HashMap<>());
        Map<Object, Object> map = new HashMap<>();
        List<PetsModel> models = new LinkedList<>();
        String today = DateUtils.getCurrentTimeStr();
        int i = 0;
        for(Pets pets : petsList){
            PetsModel petsModel = new PetsModel();
            petsModel.setId(i);
            i++;
            petsModel.setLevel(pets.getLevel().intValue());
            petsModel.setImgUrl(pets.getImgUrl());
            petsModel.setName(pets.getName());
            petsModel.setPayPrice(pets.getPriceMin().setScale(0, BigDecimal.ROUND_HALF_UP) + "-" + pets.getPriceMix().setScale(0, BigDecimal.ROUND_HALF_UP));
            String dates = pets.getStartTime() + "-" + pets.getEndTime();
            petsModel.setDateSection(dates);
            petsModel.setPriceSection(pets.getAppointmentAmount().setScale(0, BigDecimal.ROUND_HALF_UP) + "/" + pets.getPayAmount().setScale(0, BigDecimal.ROUND_HALF_UP) + " MEPC");
            petsModel.setProfit(pets.getProfitDays() + "天/" + pets.getProfitRate().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
            petsModel.setTimestamp(0 - DateUtils.secondBetween(DateUtils.getCurrentDateStr() + " " + pets.getStartTime() + ":02"));
            String startTime = pets.getStartTime();
            String endTime = pets.getEndTime();
            startTime = new StringBuilder(today).replace(11, 16, startTime).replace(17, 19,"00").toString();
            endTime = new StringBuilder(today).replace(11, 16, endTime).replace(17, 19,"00").toString();
            petsModel.setStartTime(startTime);
            String canBuyTime = sysparamsService.getValStringByKey(SystemParams.CAN_BUY_TIME);
            int buyTime = Integer.parseInt(canBuyTime);
            String waitAppointmentTime = sysparamsService.getValStringByKey(SystemParams.WAIT_APPOINTMENT_TIME);
            int waiTime = Integer.parseInt(waitAppointmentTime);

            if(pets.getState() == GlobalParams.INACTIVE){
                petsModel.setState(GlobalParams.PET_STATE_6);
            }else
            if(DateUtils.secondBetween(startTime) > 120){
                petsModel.setState(5);
            }else
            if(DateUtils.secondBetween(startTime) > 0 && DateUtils.secondBetween(startTime) < buyTime){
                petsModel.setState(GlobalParams.PET_STATE_2);
            }else
            //开始前5分钟 变为待领养 不可操作
            if(DateUtils.minBetween(startTime) > -waiTime && DateUtils.minBetween(startTime) < 0){
                petsModel.setState(GlobalParams.PET_STATE_7);
            }else
            //抢购前10分钟把状态设为可预约状态
            if(DateUtils.minBetween(endTime) < 0){
                //查看用户是否预约
                String appointmentState = RedisUtil.searchString(redis, String.format(RedisKey.BUY_APPOINTMENT_USER, pets.getLevel(), users.getId()));
                //抢购前
                if(DateUtils.secondBetween(startTime) < 0){
                    if(StrUtils.isBlank(appointmentState)) {
                        petsModel.setState(GlobalParams.PET_STATE_0);
                    }else {
                        petsModel.setState(GlobalParams.PET_STATE_1);
                    }
                //时间到
                }else{
                    if(DateUtils.secondBetween(startTime) >= 0 && DateUtils.secondBetween(endTime) < 0) {
                        String redisKey = String.format(RedisKey.PETS_LIST_WAIT_APPOINTMENT, pets.getLevel());
                        long size = RedisUtil.searchListSize(redis, redisKey);
                        if (size == 0) {
                            petsModel.setState(GlobalParams.PET_STATE_5);
                        } else {
                            petsModel.setState(GlobalParams.PET_STATE_2);
                        }
                    }else{
                        petsModel.setState(GlobalParams.PET_STATE_5);
                    }
                }
            }else{
                petsModel.setState(GlobalParams.PET_STATE_5);
            }
            models.add(petsModel);
        }
        result.put("pets", models);
        return Result.toResult(ResultCode.SUCCESS, result);
    }

    @Override
    public String get(Users users, Integer id) {
        Pets pets = petsService.selectByLevel(id);
        PetsModel petsModel = new PetsModel();
        petsModel.setLevel(pets.getLevel().intValue());
        petsModel.setImgUrl(pets.getImgUrl());
        petsModel.setName(pets.getName());
        petsModel.setPayPrice(pets.getPriceMin().setScale(0, BigDecimal.ROUND_HALF_UP) + "-" + pets.getPriceMix().setScale(0, BigDecimal.ROUND_HALF_UP));
        String dates = pets.getStartTime() + "-" + pets.getEndTime();
        petsModel.setDateSection(dates);
        petsModel.setPriceSection(pets.getAppointmentAmount().setScale(0, BigDecimal.ROUND_HALF_UP) + "/" + pets.getPayAmount().setScale(0, BigDecimal.ROUND_HALF_UP) + " MEPC");
        petsModel.setProfit(pets.getProfitDays() + "天/" + pets.getProfitRate().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
        petsModel.setTimestamp(0 - DateUtils.secondBetween(DateUtils.getCurrentDateStr() + " " + pets.getStartTime() + ":02"));
        String startTime = pets.getStartTime();
        String endTime = pets.getEndTime();
        String today = DateUtils.getCurrentTimeStr();
        startTime = new StringBuilder(today).replace(11, 16, startTime).replace(17, 19,"00").toString();
        endTime = new StringBuilder(today).replace(11, 16, endTime).replace(17, 19,"00").toString();
        petsModel.setStartTime(startTime);
        String waitAppointmentTime = sysparamsService.getValStringByKey(SystemParams.WAIT_APPOINTMENT_TIME);
        String canBuyTime = sysparamsService.getValStringByKey(SystemParams.CAN_BUY_TIME);
        int buyTime = Integer.parseInt(canBuyTime);
        int waiTime = Integer.parseInt(waitAppointmentTime);
        if(pets.getState() == GlobalParams.INACTIVE){
            petsModel.setState(GlobalParams.PET_STATE_6);
        }else
        //开始前5分钟 变为待领养 不可操作
        if(DateUtils.minBetween(startTime) > -waiTime && DateUtils.minBetween(startTime) < 0){
            petsModel.setState(GlobalParams.PET_STATE_7);
        }else
        if(DateUtils.secondBetween(startTime) > 120){
            petsModel.setState(GlobalParams.PET_STATE_5);
        }else
        if(DateUtils.secondBetween(startTime) > 0 && DateUtils.secondBetween(startTime) < buyTime){
            petsModel.setState(GlobalParams.PET_STATE_2);
        }else
        //抢购前10分钟把状态设为可预约状态
        if(DateUtils.minBetween(endTime) < 0){
            //查看用户是否预约
            String appointmentState = RedisUtil.searchString(redis, String.format(RedisKey.BUY_APPOINTMENT_USER, pets.getLevel(), users.getId()));
            //抢购前n分钟
            if(DateUtils.secondBetween(startTime) < 0){
                if(StrUtils.isBlank(appointmentState)) {
                    petsModel.setState(GlobalParams.PET_STATE_0);
                }else {
                    petsModel.setState(GlobalParams.PET_STATE_1);
                }
                //时间到
            }else{
                if(DateUtils.secondBetween(startTime) >= 0 && DateUtils.secondBetween(endTime) < 0) {
                    String redisKey = String.format(RedisKey.PETS_LIST_WAIT_APPOINTMENT, pets.getLevel());
                    long size = RedisUtil.searchListSize(redis, redisKey);
                    if (size == 0) {
                        petsModel.setState(GlobalParams.PET_STATE_5);
                    } else {
                        petsModel.setState(GlobalParams.PET_STATE_2);
                    }
                }else{
                    petsModel.setState(GlobalParams.PET_STATE_5);
                }
            }
        }else{
            petsModel.setState(GlobalParams.PET_STATE_5);
        }
        return Result.toResult(ResultCode.SUCCESS, petsModel);
    }
}