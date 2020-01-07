package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.SmsCodeBiz;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SmsTemplateCode;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PetsModel;
import com.zh.module.service.*;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.FeigeSmsUtils;
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
public class SmsCodeBizImpl implements SmsCodeBiz {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String getValidateCode(String phone, Integer type) {
        Map<String, Object> data = new HashMap<String, Object>();
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.SMS_ONOFF);
        /*判断功能是否关闭*/
        if(systemParam.getKeyval().equals("-1")){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

        /*每分钟最多一次*/
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        List<SmsRecord> list = smsRecordService.queryListByTimeLimit(map);
        if(list.size() != 0){
            return Result.toResult(ResultCode.SMS_COUNTS_LIMIT_ERROR);
        }

        Users user = usersService.selectByPhone(phone);
        if(type == GlobalParams.SMS_CODE_REGIESTER){
            /*判断功能是否关闭*/
            Sysparams registerParam = sysparamsService.getValByKey(SystemParams.REGIST_ONOFF);
            if(registerParam == null || registerParam.getKeyval().equals("-1")){
                return Result.toResult(ResultCode.PERMISSION_REGISTER_NO_ACCESS);
            }
            /*注册时判断手机号是不是已经注册*/
            if(user != null){
                return Result.toResult(ResultCode.USER_HAS_EXISTED);
            }
        }else if(type == GlobalParams.SMS_CODE_OTHER){
            /*其他情况判断用户状态*/
            if(user == null){
                return Result.toResult(ResultCode.USER_NOT_EXIST);
            }else if(user.getState() == GlobalParams.FORBIDDEN){
                return Result.toResult(ResultCode.USER_ACCOUNT_FORBIDDEN);
            }else if(user.getState() ==GlobalParams.LOGOFF){
                return Result.toResult(ResultCode.USER_ACCOUNT_LOGOFF);
            }
        }else{
            //参数类型无效
            return Result.toResult(ResultCode.PARAM_IS_INVALID);
        }

        /*发送短信，并处理结果*/
        FeigeSmsUtils feigeSmsUtils = new FeigeSmsUtils();
        JSONObject codeJson = feigeSmsUtils.getFeiGeValidateCode(phone, SmsTemplateCode.SMS_VALIDATE_CODE);
        String state =codeJson.getString("code");
        String code = "";

        if(state.equals("200")){
            code = codeJson.getString("obj");
            if(code==null){
                return Result.toResult(ResultCode.SMS_INTERFACE_ERROR);
            }
        }else if(state.equals("416")){
            return Result.toResult(ResultCode.SMS_FREQUENT_SEND);
        }else{
            return Result.toResult(ResultCode.SMS_INTERFACE_ERROR);
        }

        /*保存短信记录*/
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setPhone(phone);
        smsRecord.setType(type);
        smsRecord.setState(200);
        smsRecord.setCode(code);
        smsRecord.setTimes(GlobalParams.ACTIVE);
        smsRecordService.insert(smsRecord);

        data.put("codeId", smsRecord.getId());
        return Result.toResult(ResultCode.SUCCESS, data);
    }
}