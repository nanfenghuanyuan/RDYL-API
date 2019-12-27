package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zh.module.biz.UsersBiz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.encrypt.MD5;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
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
public class UsersBizImpl implements UsersBiz {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private RedisTemplate<String,String> redis;
    @Override
    public String login(Users user) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Users userForBase = usersService.selectByPhone(user.getPhone());
        if (!userForBase.getPassword().equals(MD5.getMd5(user.getPassword()))){
            return Result.toResult(ResultCode.USER_LOGIN_ERROR);
        }
        /*用户状态校验*/
        if(userForBase.getState() == GlobalParams.FORBIDDEN){
            return Result.toResult(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        if(userForBase.getState() == GlobalParams.LOGOFF){
            return Result.toResult(ResultCode.USER_ACCOUNT_LOGOFF);
        }
        String token = getToken(userForBase);
        jsonObject.put("token", token);
        jsonObject.put("user", userForBase);
        return Result.toResult(ResultCode.SUCCESS, jsonObject);
    }

    @Override
    public String register(String phone, String password, String uuid, Integer codeId, String code) throws Exception {
        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REGIST_ONOFF);
        if("-1".equals(systemParam.getKeyval())){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

        /*校验验证码是否正确*/
        /*SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, phone);
        if(sms == null || !code.equals(sms.getCode())){
            if(validateErrorTimesOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }else{
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
        }
        *//*校验验证码有效期*//*
        Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
        int interval = (int) ((System.currentTimeMillis() - sms.getCreatetime().getTime()) / (1000*60));
        if(timeLimit == null || sms.getTimes() != GlobalParams.ACTIVE || interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }
*/
        /*手机号是否存在*/
        Map<Object, Object> param = new HashMap<>();
        param.put("phone", phone);
        List<Users> userList = usersService.selectAll(param);
        if(userList.size() != 0){
            return Result.toResult(ResultCode.USER_HAS_EXISTED);
        }
        /*校验推荐人是否有效*/
        Users referUser = null;
        if(!StrUtils.isBlank(uuid)){
            referUser = usersService.selectByUUID(uuid);
            if(referUser == null){
                return Result.toResult(ResultCode.REFER_USER_NOT_EXIST);
            }
            if(referUser.getState() == GlobalParams.FORBIDDEN){
                return Result.toResult(ResultCode.REFER_USER_ACCOUNT_FORBIDDEN);
            }
            if(referUser.getState() == GlobalParams.LOGOFF){
                return Result.toResult(ResultCode.REFER_USER_ACCOUNT_LOGOFF);
            }
        }
        //保存用户
        Integer uuids = getUUID();
        Users users = new Users();
        users.setAccount(phone);
        users.setContribution(0);
        users.setNickName(phone);
        users.setState((byte) GlobalParams.INACTIVE);
        users.setPhone(phone);
        users.setPassword(MD5.getMd5(password));
        users.setTeamLevel((byte) 0);
        users.setPersonLevel((byte) 0);
        users.setReferId(uuid);
        users.setUuid(uuids.toString());
        usersService.insertSelective(users);

        /*初始化账户*/
        List<Integer> coinList = new LinkedList<>();
        coinList.add(CoinType.CNY);
        coinList.add(CoinType.OS);
        for (Integer coinType : coinList) {
            Account account = new Account();
            account.setAccountType(AccountType.ACCOUNT_TYPE_ACTIVE);
            account.setUserId(users.getId());
            account.setAvailbalance(BigDecimal.ZERO);
            account.setFrozenblance(BigDecimal.ZERO);
            account.setCoinType(coinType);
            accountService.insertSelective(account);
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    /**
     * 获取JWTtoken
     * @param user
     * @return
     */
    private String getToken(Users user) {
        String token = "";
        // 将 user id 保存到 token 里面
        token= JWT.create().withAudience(user.getId().toString())
                // 以 password 作为 token 的密钥
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    /**
     * 验证验证码的错误次数
     * @param codeId
     * @return int
     * @date 2018-2-2
     * @author lina
     */
    public boolean validateErrorTimesOfSms(Integer codeId){
        String key = String.format(RedisKey.SMS_ERROR_TIMES, codeId);
        String val = RedisUtil.searchString(redis, key);
        int next = 1;
        if(StrUtils.isBlank(val)){
            RedisUtil.addString(redis, key, 60*10, next+"");
        }else{
            next = Integer.parseInt(val)+1;
            RedisUtil.addString(redis, key, 60*10,next+"" );

            Sysparams countLimit = sysparamsService.getValByKey(SystemParams.SMS_COUNTS_LIMIT);
            if(countLimit!=null){
                int limit = Integer.parseInt(countLimit.getKeyval());
                if(next>limit){
                    return false;
                }
            }

        }
        return true;
    }
    private boolean validataStateOfSms(Integer codeId){
        String key = String.format(RedisKey.SMS_ERROR_TIMES, codeId);
        String val = RedisUtil.searchString(redis, key);
        if(StrUtils.isBlank(val)){
            return true;
        }else{
            Sysparams countLimit = sysparamsService.getValByKey(SystemParams.SMS_COUNTS_LIMIT);
            if(countLimit!=null){
                if(Integer.parseInt(val)>Integer.parseInt(countLimit.getKeyval())){
                    return false;
                }
            }
        }
        return true;
    }
    private Integer getUUID(){
        Integer uuid = UUIDs.getUUID8();
        if(uuid < 10000000){
            return getUUID();
        }
        Users uuser = usersService.selectByUUID(uuid.toString());
        if(uuser == null){
            return uuid;
        }else{
            return getUUID();
        }
    }

    @Override
    public String updatePassword(Users user, String oldPassword, String password, String code, Integer codeId) throws Exception {
        //校验验证码是否正确
        SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, user.getPhone());
        if(sms == null || !code.equals(sms.getCode())){
            if(validateErrorTimesOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }else{
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
        }
        //校验验证码有效期
        Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
        int interval = (int) ((System.currentTimeMillis() - sms.getCreatetime().getTime()) / (1000*60));
        if(timeLimit == null || sms.getTimes() != GlobalParams.ACTIVE || interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }
        if(!user.getPassword().equals(MD5.getMd5(oldPassword))){
            Result.toResult(ResultCode.USER_OLD_PASSWORD_ERROR);
        }
        user.setPassword(MD5.getMd5(password));
        usersService.updateByPrimaryKeySelective(user);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String updateOrderPassword(Users user, String oldPassword, String password, String code, Integer codeId) throws Exception {
        //校验验证码是否正确
        SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, user.getPhone());
        if(sms == null || !code.equals(sms.getCode())){
            if(validateErrorTimesOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }else{
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
        }
        //校验验证码有效期
        Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
        int interval = (int) ((System.currentTimeMillis() - sms.getCreatetime().getTime()) / (1000*60));
        if(timeLimit == null || sms.getTimes() != GlobalParams.ACTIVE || interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }
        if(!user.getOrderPwd().equals(MD5.getMd5(oldPassword))){
            Result.toResult(ResultCode.USER_OLD_PASSWORD_ERROR);
        }
        user.setOrderPwd(MD5.getMd5(password));
        usersService.updateByPrimaryKeySelective(user);
        return Result.toResult(ResultCode.SUCCESS);
    }
}
