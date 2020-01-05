package com.zh.module.biz.impl;

import com.zh.module.biz.TeamBiz;
import com.zh.module.constants.GlobalParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PageModel;
import com.zh.module.model.TeamListModel;
import com.zh.module.service.*;
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
    private UsersService usersService;
    @Autowired
    private ProfitRecordService profitRecordService;
    @Autowired
    private RedisTemplate<String,String> redis;


    @Override
    public String init(Users users, Integer type, PageModel pageModel) {
        Map<String, Object> result = new HashMap<>();
        Map<Object, Object> param = new HashMap<>();
        param.put("referId", users.getUuid());
        //直推人数
        int personAmount = usersService.selectCount(param);
        result.put("personAmount", personAmount);
        //有效人数
        param.put("effective", GlobalParams.ACTIVE);
        personAmount = usersService.selectCount(param);
        result.put("effectiveAmount", personAmount);
        String profit = profitRecordService.selectSumAmount(users.getId(), GlobalParams.PROFIT_RECORD_TEAM);
        result.put("profit", profit);
        List<TeamListModel> models = new LinkedList<>();
        param = new HashMap<>();
        param.put("referId", users.getUuid());
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<Users> usersList = usersService.selectPaging(param);
        if(type == 1){
            for(Users user : usersList){
                TeamListModel teamListModel = new TeamListModel();
                teamListModel.setPhone(user.getPhone());
                teamListModel.setIdStatus(user.getIdStatus().intValue());
                teamListModel.setLevel(user.getTeamLevel().intValue());
                profit = profitRecordService.selectSumAmount(user.getId(), GlobalParams.PROFIT_RECORD_TEAM);
                teamListModel.setAmount(profit);
                models.add(teamListModel);
            }
        }else if(type == 2){
            for(Users user : usersList){
                param = new HashMap<>();
                param.put("referId", user.getUuid());
                List<Users> referUserList = usersService.selectAll(param);
                for(Users referUser : referUserList) {
                    TeamListModel teamListModel = new TeamListModel();
                    teamListModel.setPhone(referUser.getPhone());
                    teamListModel.setIdStatus(referUser.getIdStatus().intValue());
                    teamListModel.setLevel(referUser.getTeamLevel().intValue());
                    profit = profitRecordService.selectSumAmount(referUser.getId(), GlobalParams.PROFIT_RECORD_TEAM);
                    teamListModel.setAmount(profit);
                    models.add(teamListModel);
                }
            }
        }else{
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }
        result.put("list", models);
        return Result.toResult(ResultCode.SUCCESS, result);
    }
}