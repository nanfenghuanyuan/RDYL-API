package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.biz.BindInfoBiz;
import com.zh.module.biz.HomeBiz;
import com.zh.module.constants.GlobalParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.Banner;
import com.zh.module.entity.BindInfo;
import com.zh.module.entity.Pets;
import com.zh.module.entity.Users;
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
public class BindInfoBizImpl implements BindInfoBiz {
    @Autowired
    private BindInfoService bindInfoService;
    @Override
    public String getBindInfo(Users users) {
        List<BindInfo> list = bindInfoService.queryByUser(users.getId());
        return Result.toResult(ResultCode.SUCCESS, list);
    }

    @Override
    public String binding(Users users, JSONObject params) {
        BindInfo bindInfo = new BindInfo();
        Integer type = params.getInteger("type");
        bindInfo.setType(type.byteValue());
        bindInfo.setUserId(users.getId());
        bindInfo.setState((byte) GlobalParams.ACTIVE);
        bindInfo.setImgUrl(params.getString("imgUrl"));
        String account = params.getString("account");
        bindInfo.setAccount(account);
        String name = params.getString("name");
        bindInfo.setName(name);
        if(type.equals(GlobalParams.PAY_BANK)){
            String bankName = params.getString("bankName");
            bindInfo.setBankName(bankName);
            String branchName = params.getString("branchName");
            bindInfo.setBranchName(branchName);
        }
        bindInfoService.insertSelective(bindInfo);
        return Result.toResult(ResultCode.SUCCESS);
    }
}