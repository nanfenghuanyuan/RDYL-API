package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zh.module.biz.SystemBiz;
import com.zh.module.biz.UsersBiz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.encrypt.MD5;
import com.zh.module.entity.Account;
import com.zh.module.entity.Notice;
import com.zh.module.entity.Sysparams;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.Configuration;
import com.zh.module.service.*;
import com.zh.module.utils.RedisUtil;
import com.zh.module.utils.StrUtils;
import com.zh.module.utils.UUIDs;
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
public class SystemBizImpl implements SystemBiz {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String getStartupParam() {
        Notice notice = noticeService.seletByStart();
        Configuration config = new Configuration();
        config.setNotice(notice);
        return Result.toResult(ResultCode.SUCCESS, config);
    }
}
