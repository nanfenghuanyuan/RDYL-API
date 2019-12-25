package com.zh.module.biz.impl;

import com.zh.module.biz.HomeBiz;
import com.zh.module.constants.GlobalParams;
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
        List<Pets> petsList = petsService.selectAll(param);
        Map<Object, Object> map = new HashMap<>();
        List<PetsModel> models = new LinkedList<>();
        String today = DateUtils.getCurrentTimeStr();
        for(Pets pets : petsList){
            PetsModel petsModel = new PetsModel();
            petsModel.setImgUrl(pets.getImgUrl());
            petsModel.setName(pets.getName());
            petsModel.setPayPrice(pets.getPriceMin() + "-" + pets.getPriceMix());
            petsModel.setDateSection(pets.getStartTime() + "-" + pets.getEndTime());
            petsModel.setPriceSection(pets.getAppointmentAmount() + "/" + pets.getPayAmount());
            petsModel.setProfit(pets.getProfitDays() + "天/" + pets.getProfitRate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            String startTime = pets.getStartTime();
            String endTime = pets.getEndTime();
            startTime = new StringBuilder(today).replace(11, 16, startTime).toString();
            endTime = new StringBuilder(today).replace(11, 16, endTime).toString();
            //抢购前10分钟把状态设为可预约状态
            if(DateUtils.minBetween(startTime) > -10 && DateUtils.minBetween(endTime) < 0){
                //查看用户是否预约
                String appointmentState = RedisUtil.searchString(redis, String.format(RedisKey.BUY_APPOINTMENT_USER, pets.getLevel(), users.getId()));
                if(StrUtils.isBlank(appointmentState)) {
                    petsModel.setState(GlobalParams.PET_STATE_0);
                }else{
                    //如果用户已预约且到领养时间后，则可领养；否则显示已预约
                    if(DateUtils.minBetween(startTime) >= 0 && DateUtils.minBetween(endTime) < 0){
                        map = new HashMap<>();
                        map.put("buyUserId", users.getId());
                        map.put("level", pets.getLevel());
                        map.put("state", GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
                        //看看用户是否存在已预约订单
                        int count = petsMatchingListService.selectCount(map);
                        if(count != 0){
                            petsModel.setState(GlobalParams.PET_STATE_2);
                        }else {
                            petsModel.setState(GlobalParams.PET_STATE_3);
                        }
                    }else{
                        petsModel.setState(GlobalParams.PET_STATE_1);
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
}