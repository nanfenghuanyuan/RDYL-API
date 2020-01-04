package com.zh.module.biz.impl;

import com.zh.module.biz.TeamBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.TeamListModel;
import com.zh.module.service.BannerService;
import com.zh.module.service.PetsListService;
import com.zh.module.service.PetsMatchingListService;
import com.zh.module.service.PetsService;
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
public class TeamBizImpl implements TeamBiz {
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
    public String init(Users users, Integer type) {
        Map<String, Object> result = new HashMap<>();
        result.put("personAmount", 10);
        result.put("effectiveAmount", 9);
        result.put("profit", 200);
        List<TeamListModel> models = new LinkedList<>();
        TeamListModel teamListModel = new TeamListModel();
        teamListModel.setAmount(new BigDecimal(20));
        teamListModel.setIdStatus(1);
        teamListModel.setPhone("133****3333");
        teamListModel.setLevel(2);
        models.add(teamListModel);
        models.add(teamListModel);
        models.add(teamListModel);
        models.add(teamListModel);
        result.put("list", models);
        return Result.toResult(ResultCode.SUCCESS, result);
    }
}