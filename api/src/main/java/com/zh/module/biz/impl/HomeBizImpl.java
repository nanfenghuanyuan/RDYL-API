package com.zh.module.biz.impl;

import com.zh.module.biz.HomeBiz;
import com.zh.module.constants.GlobalParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PetsModel;
import com.zh.module.service.*;
import com.zh.module.utils.DateUtils;
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
        result.put("banner", bannerList);
        List<Pets> petsList = petsService.selectAll(param);
        PetsModel petsModel = new PetsModel();
        Map<Object, Object> map = new HashMap<>();
        for(Pets pets : petsList){
            petsModel.setImgUrl(pets.getImgUrl());
            petsModel.setName(pets.getName());
            petsModel.setPayPrice(pets.getPriceMin() + "-" + pets.getPriceMix());
            petsModel.setPriceSection(pets.getAppointmentAmount() + "/" + pets.getPayAmount());
            petsModel.setProfit(pets.getProfitDays() + "天/" + pets.getProfitRate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            map = new HashMap<>();
            map.put("buyUserId", users.getId());
            map.put("level", pets.getLevel());
            String startTime = pets.getStartTime();
            String endTime = pets.getEndTime();
            //抢购前10分钟把状态设为可预约状态
            if(DateUtils.minBetween(startTime) > -10 && DateUtils.minBetween(endTime) < 0){
                petsModel.setState(GlobalParams.PET_STATE_0);
            }
            List<PetsMatchingList> petsMatchingLists = petsMatchingListService.selectByHomePage(map);
            List<PetsList> petsLists = petsListService.selectByHomePage(map);
        }
        result.put("pets", petsList);
        return Result.toResult(ResultCode.SUCCESS, result);
    }
}