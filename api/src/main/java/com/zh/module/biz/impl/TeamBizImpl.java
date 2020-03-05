package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.biz.TeamBiz;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.TeamType;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PageModel;
import com.zh.module.model.TeamListModel;
import com.zh.module.service.*;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.StrUtils;
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
        //团队
        JSONObject jsonObject = getTeam(new JSONObject(), users.getUuid());
        List<Integer> team = new LinkedList<>();
        team.add(jsonObject.getIntValue("allNumber"));
        team.add(jsonObject.getIntValue("activeNumber"));
        team.add(jsonObject.getIntValue("effectiveNumber"));
        result.put("team", team);
        //一级
        List<Integer> one = getOne(users.getUuid());
        result.put("one", one);
        //二级
        List<Integer> two = getTwo(users.getUuid());
        result.put("two", two);
        List<TeamListModel> models = new LinkedList<>();
        param = new HashMap<>();
        param.put("referId", users.getUuid());
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<Users> usersList = usersService.selectPaging(param);
        if(type == 1){
            for(Users user : usersList){
                TeamListModel teamListModel = new TeamListModel();
                teamListModel.setName(user.getNickName());
                teamListModel.setTime(DateUtils.getDateFormate2(user.getCreateTime()));
                teamListModel.setPhone(user.getPhone());
                teamListModel.setIdStatus(user.getState().intValue());
                models.add(teamListModel);
            }
        }else if(type == 2){
            for(Users user : usersList){
                param = new HashMap<>();
                param.put("referId", user.getUuid());
                List<Users> referUserList = usersService.selectAll(param);
                for(Users referUser : referUserList) {
                    TeamListModel teamListModel = new TeamListModel();
                    teamListModel.setName(referUser.getNickName());
                    teamListModel.setTime(DateUtils.getDateFormate2(referUser.getCreateTime()));
                    teamListModel.setPhone(referUser.getPhone());
                    teamListModel.setIdStatus(referUser.getState().intValue());
                    models.add(teamListModel);
                }
            }
        }else{
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }
        result.put("list", models);
        return Result.toResult(ResultCode.SUCCESS, result);
    }

    private List<Integer> getTwo(String uuid) {
        List<Integer> integers = new LinkedList<>();
        List<Users> list = usersService.selectByReferID(uuid);
        int activeNumber = 0;
        int effectiveNumber = 0;
        int allNumber = 0;
        for(Users users : list){
            List<Users> lists = usersService.selectByReferID(users.getUuid());
            for(Users users1: lists){
                if(users1.getIdStatus() == GlobalParams.ACTIVE){
                    activeNumber ++;
                }
                if(users1.getEffective() == GlobalParams.ACTIVE){
                    effectiveNumber ++;
                }
                allNumber ++;
            }
        }
        integers.add(allNumber);
        integers.add(activeNumber);
        integers.add(effectiveNumber);
        return integers;
    }

    private List<Integer> getOne(String uuid) {
        List<Integer> integers = new LinkedList<>();
        List<Users> list = usersService.selectByReferID(uuid);
        int activeNumber = 0;
        int effectiveNumber = 0;
        for(Users users : list){
            if(users.getIdStatus() == GlobalParams.ACTIVE){
                activeNumber ++;
            }
            if(users.getEffective() == GlobalParams.ACTIVE){
                effectiveNumber ++;
            }
        }
        integers.add(list.size());
        integers.add(activeNumber);
        integers.add(effectiveNumber);
        return integers;
    }

    private JSONObject getTeam(JSONObject json, String uuid){

        List<Users> list = usersService.selectByReferID(uuid);
        if(list.size() == 0){
            return json;
        }
        for(Users users : list){
            int allNumber = 0;
            int activeNumber = 0;
            int effectiveNumber = 0;
            if(users.getIdStatus() == GlobalParams.ACTIVE){
                activeNumber ++;
            }
            if(users.getEffective() == GlobalParams.ACTIVE){
                effectiveNumber ++;
            }
            allNumber ++;
            json.put("allNumber", json.getIntValue("allNumber") + allNumber);
            json.put("activeNumber", json.getIntValue("activeNumber") + activeNumber);
            json.put("effectiveNumber", json.getIntValue("effectiveNumber") + effectiveNumber);
            getTeam(json, users.getUuid());
        }
        return json;
    }
}