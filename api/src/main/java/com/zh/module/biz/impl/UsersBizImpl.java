package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zh.module.aliyun.H5RpBasic;
import com.zh.module.aliyun.MaterialModel;
import com.zh.module.biz.IdCardValidateBiz;
import com.zh.module.biz.UsersBiz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.encrypt.MD5;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.UsersModel;
import com.zh.module.service.*;
import com.zh.module.utils.*;
import com.zh.module.variables.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
@Transactional
@Slf4j
public class UsersBizImpl implements UsersBiz {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private H5RpBasic h5RpBasic;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private IdCardValidateBiz idcardValidateBiz;
    @Resource
    private RedisTemplate<String,String> redis;
    @Override
    public String login(Users user) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Users userForBase = usersService.selectByPhone(user.getPhone());
        if (userForBase == null){
            return Result.toResult(ResultCode.USER_LOGIN_ERROR);
        }
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
        UsersModel usersModel = new UsersModel();
        usersModel.setAccount(userForBase.getAccount());
        usersModel.setContribution(userForBase.getContribution());
        usersModel.setId(userForBase.getId());
        usersModel.setNickName(userForBase.getNickName());
        usersModel.setPhone(userForBase.getPhone());
        usersModel.setState(userForBase.getState().intValue());
        usersModel.setTeamLevel(userForBase.getTeamLevel().intValue());
        boolean orderPasswordStatus = StrUtils.isBlank(userForBase.getOrderPwd());
        usersModel.setUuid(userForBase.getUuid());
        usersModel.setOrderPasswordStatus(!orderPasswordStatus);
        String token = getTokens(userForBase);
        jsonObject.put("token", token);
        jsonObject.put("user", usersModel);
        String goldAmount = accountService.selectSumAmountByAccountTypeAndCoinType(user.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS);
        goldAmount = StrUtils.isBlank(goldAmount) ? "0" : goldAmount;
        jsonObject.put("goldAmount", new BigDecimal(goldAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
        String holdAssets = petsListService.selectSumAmountByUser(user.getId());
        holdAssets = StrUtils.isBlank(holdAssets) ? "0" : holdAssets;
        jsonObject.put("holdAssets", new BigDecimal(holdAssets).setScale(0, BigDecimal.ROUND_HALF_UP));
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
    private String getTokens(Users user) {
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
    public String updatePassword(Users user, String password, String code, Integer codeId) throws Exception {
        //校验验证码是否正确
        /*SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, user.getPhone());
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
        }*/
        user.setPassword(MD5.getMd5(password));
        usersService.updateByPrimaryKeySelective(user);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String updateOrderPassword(Users user, String password, String code, Integer codeId) throws Exception {
        //校验验证码是否正确
        /*SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, user.getPhone());
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
        }*/
        user.setOrderPwd(MD5.getMd5(password));
        usersService.updateByPrimaryKeySelective(user);
        return Result.toResult(ResultCode.SUCCESS);
    }
    @Override
    public String getToken(Users user, String name, String idCard) {
        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REAL_NAME_ONOFF);
        if(systemParam==null||systemParam.getKeyval().equals("-1")){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        if(user.getIdStatus() != GlobalParams.REALNAME_NEW_STATE_NO){
            return Result.toResult(ResultCode.USER_REALNAME_ERROR);
        }

        /*判断验证次数*/
        Map<String, Object> countMap = idcardValidateBiz.queryValidateTimes(user.getId(),3);
        Sysparams timesLimit = sysparamsService.getValByKey(SystemParams.IDCARD_VALIDATE_TIMES_LIMIT);
        if(timesLimit==null){
            return Result.toResult(ResultCode.SYSTEM_PARAM_ERROR);
        }
        int times = Integer.parseInt(timesLimit.getKeyval());
        if(countMap!=null&&times>0){
            //当日认证次数
            BigInteger dateCount = (BigInteger)countMap.get(DateUtils.getCurrentDateStr());
            if(dateCount!=null&&dateCount.intValue()>=times){
                return Result.toResult(ResultCode.REAL_NAME_LIMIT);
            }

            //连续两天次数限制
            BigInteger dateCount1 = (BigInteger)countMap.get(DateUtils.getSomeDay(-1));
            BigInteger dateCount2 = (BigInteger)countMap.get(DateUtils.getSomeDay(-2));
            BigInteger dateCount3 = (BigInteger)countMap.get(DateUtils.getSomeDay(-3));
            if((dateCount1!=null&&dateCount1.intValue()>=times&&dateCount2!=null&&dateCount2.intValue()>=times)
                    ||(dateCount2!=null&&dateCount2.intValue()>=times&&dateCount3!=null&&dateCount3.intValue()>=times)){
                return Result.toResult(ResultCode.REAL_NAME_LIMIT);
            }
        }
        JSONObject jsonObject = h5RpBasic.init(name, idCard);
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("token", jsonObject.getString("token"));
        map.put("taskId", jsonObject.getString("ticketId"));
        map.put("url", jsonObject.getString("url"));

        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String getStatus(Users user, String taskId) {
        /*用户是否已经实名*/
        if(user.getIdStatus() != GlobalParams.REALNAME_NEW_STATE_NO){
            return Result.toResult(ResultCode.USER_REALNAME_ERROR);
        }

        JSONObject jsonObject = h5RpBasic.getStatus(taskId);
        System.out.println(jsonObject.toJSONString());
        int status = -1;
        //认证记录不存在，直接返回
        if(status == GlobalParams.REALNAME_STATE_NOT_EXIST){
            return Result.toResult(ResultCode.REAL_NAME_TASK_NOT_EXIST);
        }
        //认证中，等待两秒继续请求一次
        if(status == GlobalParams.REALNAME_STATE_ING){
            try {
                TimeUnit.SECONDS.sleep(2);
                status = 0;
            } catch (InterruptedException e) {
                log.info("实人认证等待被打断---");
                e.printStackTrace();
            }
        }
        HashMap<String, Object> map = new HashMap<>();

        IdcardValidate iv = new IdcardValidate();
        ResultCode code = ResultCode.REAL_NAME_FAIL;
        if(status == GlobalParams.REALNAME_STATE_SUCCESS || status == GlobalParams.REALNAME_STATE_FAIL){
            MaterialModel mate = new MaterialModel();
            BeanUtils.copyProperties(mate, iv);
            iv.setState(status);
            iv.setName(mate.getName());
            iv.setAddress(mate.getAddress());
            iv.setFacepic(mate.getFacePic());
            iv.setIdcardbackpic(mate.getIdCardBackPic());
            iv.setIdcardexpiry(mate.getIdCardExpiry());
            iv.setIdcardfrontpic(mate.getIdCardFrontPic());
            iv.setSex(mate.getSex());
            iv.setIdcardtype(mate.getIdCardType());
            iv.setTaskid(taskId);
            iv.setUserid(user.getId());
            iv.setIdentificationnumber(mate.getIdentificationNumber());
        }

        if(status == GlobalParams.REALNAME_STATE_SUCCESS){
            Integer oldUserId = idcardValidateBiz.getByUserByIdcard(iv.getIdentificationnumber());
            if(oldUserId != null && !oldUserId.equals(user.getId())){
                iv.setState(GlobalParams.REALNAME_STATE_IDCARD_EXIST);
                code = ResultCode.REAL_NAME_IDCARD_EXIST;
            }else{
                user.setIdStatus((byte) GlobalParams.ACTIVE);
                usersService.updateByPrimaryKeySelective(user);
                code = ResultCode.SUCCESS;
                map.put("name", iv.getName());
            }
        }
        idcardValidateBiz.insert(iv);
        return Result.toResult(code, map);
    }

}
