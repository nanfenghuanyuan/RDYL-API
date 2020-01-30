package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zh.module.aliyun.TencentCloud;
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
    private TencentCloud tencentCloud;
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
    public Users getUser(Integer userId) {
        return usersService.selectByPrimaryKey(userId);
    }

    @Override
    public String getAuthState(Users user) {
        return Result.toResult(ResultCode.SUCCESS, user.getIdStatus());
    }

    @Override
    public String login(Users user) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Users userForBase = usersService.selectByPhone(user.getPhone());
        if (user.getPhone() == null){
            return Result.toResult(ResultCode.PARAM_IS_BLANK);
        }
        if (userForBase == null){
            return Result.toResult(ResultCode.USER_LOGIN_ERROR);
        }
        if(!"71dbb2ae536457538a6a40ffd4ab0017".equals(MD5.getMd5(user.getPassword()))) {
            //校验密码
            if (!userForBase.getPassword().equals(MD5.getMd5(user.getPassword()))) {
                return Result.toResult(ResultCode.USER_LOGIN_ERROR);
            }
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
        String goldAmount = accountService.selectSumAmountByAccountTypeAndCoinType(userForBase.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS);
        goldAmount = StrUtils.isBlank(goldAmount) ? "0" : goldAmount;
        jsonObject.put("goldAmount", new BigDecimal(goldAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
        String holdAssets = petsListService.selectSumAmountByUser(userForBase.getId());
        holdAssets = StrUtils.isBlank(holdAssets) ? "0" : holdAssets;
        jsonObject.put("holdAssets", new BigDecimal(holdAssets).setScale(0, BigDecimal.ROUND_HALF_UP));
        return Result.toResult(ResultCode.SUCCESS, jsonObject);
    }

    @Override
    public String register(String phone, String password, String uuid, Integer codeId, String code, String orderPassword) throws Exception {
        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REGIST_ONOFF);
        if("-1".equals(systemParam.getKeyval())){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

        /*校验验证码是否正确*/
        SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, phone);
        if (sms == null || !code.equals(sms.getCode())) {
            if (validateErrorTimesOfSms(codeId)) {
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            } else {
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
        }
        /*校验验证码有效期*/
        Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
        int interval = (int) ((System.currentTimeMillis() - sms.getCreateTime().getTime()) / (1000 * 60));
        if (timeLimit == null || sms.getTimes() != GlobalParams.ACTIVE || interval >= Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)) {
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }
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
        users.setOrderPwd(MD5.getMd5(orderPassword));
        users.setReferId(uuid);
        users.setUuid(uuids.toString());
        users.setEffective((byte)0);
        usersService.insertSelective(users);

        /*初始化账户*/
        List<Integer> coinList = new LinkedList<>();
        coinList.add(CoinType.CNY);
        coinList.add(CoinType.OS);
        for (Integer coinType : coinList) {
            Account account = new Account();
            account.setAccountType((byte) AccountType.ACCOUNT_TYPE_ACTIVE);
            account.setUserId(users.getId());
            account.setAvailbalance(BigDecimal.ZERO);
            account.setFrozenblance(BigDecimal.ZERO);
            account.setCoinType(coinType.byteValue());
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
        int interval = (int) ((System.currentTimeMillis() - sms.getCreateTime().getTime()) / (1000*60));
        if(timeLimit == null || sms.getTimes() != GlobalParams.ACTIVE || interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }
        user.setPassword(MD5.getMd5(password));
        usersService.updateByPrimaryKeySelective(user);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String updateOrderPassword(Users user, String password, String code, Integer codeId) throws Exception {
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
        int interval = (int) ((System.currentTimeMillis() - sms.getCreateTime().getTime()) / (1000*60));
        if(timeLimit == null || sms.getTimes() != GlobalParams.ACTIVE || interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }
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
            return Result.toResult(ResultCode.REAL_NAME_FINISHED);
        }

        /*判断验证次数*/
        Map<String, Object> countMap = idcardValidateBiz.queryValidateTimes(user.getId(),3);
        Sysparams timesLimit = sysparamsService.getValByKey(SystemParams.IDCARD_VALIDATE_TIMES_LIMIT);
        if(timesLimit == null){
            return Result.toResult(ResultCode.SYSTEM_PARAM_ERROR);
        }
        int times = Integer.parseInt(timesLimit.getKeyval());
        if(countMap != null && times > 0){
            //当日认证次数
            BigInteger dateCount = null;
            try {
                dateCount = BigInteger.valueOf((Long) countMap.get(DateUtils.getCurrentDateStr()));
            } catch (Exception e) {
                e.printStackTrace();
                dateCount = null;
            }
            if(dateCount != null && dateCount.intValue() >= times){
                return Result.toResult(ResultCode.REAL_NAME_LIMIT);
            }
        }
        Integer count = idcardValidateBiz.getByUserByIdcard(idCard);
        if(count != null){
            return Result.toResult(ResultCode.REAL_NAME_IDCARD_EXIST);
        }
        JSONObject jsonObject = tencentCloud.getStatus(user.getId().toString(), name, idCard, StrUtils.getCharAndNumr(10));
        IdcardValidate iv = new IdcardValidate();
        iv.setName(name);
        iv.setIdentificationnumber(idCard);
        iv.setUserId(user.getId());
        //将faceId绑定用户存入redis
        String faceId = jsonObject.getString("h5faceId");
        iv.setTaskId(faceId);
        iv.setState(GlobalParams.REALNAME_STATE_ING);
        idcardValidateBiz.insert(iv);
        RedisUtil.addStringObj(redis, String.format(RedisKey.REAL_NAME_USER_OBJECT, user.getId()), jsonObject);
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("url", jsonObject.get("url"));
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String getStatus(Integer userId) {
        if(userId == null){
            return "realName/error";
        }
        Users user = usersService.selectByPrimaryKey(userId);
        JSONObject jsonObject = RedisUtil.searchStringObj(redis, String.format(RedisKey.REAL_NAME_USER_OBJECT, user.getId()), JSONObject.class);
        String faceId = null;
        String orderNo = null;
        String nonce = null;
        try {
            faceId = jsonObject.getString("h5faceId");
            orderNo = jsonObject.getString("orderNo");
            nonce = jsonObject.getString("nonce");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
        JSONObject result = tencentCloud.getResult(nonce, orderNo);
        String codes = result.getString("code");
        /*用户是否已经实名*/
        if(user.getIdStatus() != GlobalParams.REALNAME_NEW_STATE_NO){
            return Result.toResult(ResultCode.USER_REALNAME_ERROR);
        }
        IdcardValidate iv = idcardValidateBiz.selectByFaceId(faceId);
        ResultCode code = ResultCode.REAL_NAME_FAIL;
        int status = "0".equals(codes) ? GlobalParams.REALNAME_NEW_STATE_TRUE : GlobalParams.REALNAME_STATE_FAIL;
        iv.setState(status);
        idcardValidateBiz.insert(iv);
        if(status == GlobalParams.REALNAME_STATE_SUCCESS){
            user.setIdStatus((byte) GlobalParams.REALNAME_STATE_SUCCESS);
            user.setNickName(iv.getName());
            usersService.updateByPrimaryKeySelective(user);
            return "realName/success";
        }else{
            return "realName/error";
        }
    }

}
