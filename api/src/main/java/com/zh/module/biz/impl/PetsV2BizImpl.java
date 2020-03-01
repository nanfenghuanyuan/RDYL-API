package com.zh.module.biz.impl;

import com.zh.module.async.BuysAsync;
import com.zh.module.biz.PetsBiz;
import com.zh.module.biz.PetsV2Biz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.exception.BanlanceNotEnoughException;
import com.zh.module.model.PageModel;
import com.zh.module.model.PetsMatchingListModel;
import com.zh.module.service.*;
import com.zh.module.utils.BigDecimalUtils;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.RedisUtil;
import com.zh.module.variables.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
@Transactional
@Slf4j
public class PetsV2BizImpl extends BaseBizImpl implements PetsV2Biz {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AppointmentRecordService appointmentRecordService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private BlackListService blackListService;
    @Autowired
    private BuysAsync buysAsync;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String buy(Users users, Integer level) throws ParseException {
        String redisKey;
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        Integer userId = users.getId();
        //判断是否在黑名单
        BlackList blackList = blackListService.selectByUserId(userId);
        if(blackList != null){
            return Result.toResult(ResultCode.PETS_HAS_NONE);
        }
        /*先完成备用手机的绑定*/
        Map<Object, Object> param = new HashMap<>();
        param.put("userId", users.getId());
        param.put("type", GlobalParams.PAY_PHONE);
        BindInfo buyInfo = bindInfoService.selectByUserAndType(param);
        if(buyInfo == null){
            return Result.toResult(ResultCode.BIND_PHONE_MUST);
        }
        //每个人只允许持有一个
        Map<Object, Object> params = new HashMap<>();
        params.put("buyUserId", userId);
        params.put("level", level);
        params.put("state", GlobalParams.PET_MATCHING_STATE_NOPAY);
        int counts = petsMatchingListService.selectCount(params);
        params.put("state", GlobalParams.PET_MATCHING_STATE_PAYED);
        counts = counts + petsMatchingListService.selectCount(params);
        if(counts != 0){
            return Result.toResult(ResultCode.PERSON_HAS_PETS);
        }

        //是否在允许的时间范围内
        if(!checkIsDateLimit(level, true)){
            return Result.toResult(ResultCode.TIME_ERROR);
        }
        //查看绑定信息
        List<BindInfo> bindInfos = bindInfoService.queryByUser(userId);
        if(bindInfos.size() == 0){
            return Result.toResult(ResultCode.BIND_INFO_NONE);
        }

        redisKey = String.format(RedisKey.PETS_LIST_BUY_LIST, level);
        List<Integer> list = RedisUtil.searchStringObj(redis, redisKey, List.class);
        list = list == null ? new LinkedList<>() : list;
        list.add(userId);
        RedisUtil.addStringObj(redis, redisKey, list);

        return Result.toResult(ResultCode.SUCCESS);
    }
}