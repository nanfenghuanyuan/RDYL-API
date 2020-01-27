package com.zh.module.controller;

import com.zh.module.biz.SmsCodeBiz;
import com.zh.module.biz.UsersBiz;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.enums.ResultCode;
import com.zh.module.service.SysparamsService;
import com.zh.module.utils.PatternUtil;
import com.zh.module.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-10-17 18:53
 */
@Controller
@RequestMapping(value = "web")
public class WebController {

    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private UsersBiz usersBiz;
    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private SmsCodeBiz smsCodeBiz;
    /**
     * 跳转注册页
     * @param map
     * @return
     */
    @RequestMapping(value="register",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String toRegister(Map<String, Object> map, String uuid){
        String url = sysparamsService.getValStringByKey(SystemParams.APP_DOWNLAOD_URL);
        map.put("uuid", uuid);
        map.put("downloadUrl", url);
        return "register";
    }
    /**
     * 获取校验码
     * @param phone
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value="smscode",method= RequestMethod.POST ,produces = "application/json;charset=utf-8")
    public String getValidateCode(String phone ,Integer type,String VerificationCode){
        try {
            String randCode = RedisUtil.searchString(redis,"RandCode" + phone);
            if (!VerificationCode.equalsIgnoreCase(randCode)){
                return Result.toResult(ResultCode.VCODE_FALSE);
            }
            //获取校验码
            String returnStr = smsCodeBiz.getValidateCode(phone, type);
            return returnStr;
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SMS_ERROR);
        }
    }

    /**
     * 用户WEB注册
     * @param phone
     * @param userPassword
     * @param code
     * @param codeId
     * @param referPhone
     * @return
     */
    @ResponseBody
    @RequestMapping(value="submitRegister",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String register(String phone,String userPassword,String code,Integer codeId,String referPhone,String orderPassword){
        /*正则校验*/
        if(phone.length() != 11){
            return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
        }
        if(referPhone.length() != 8){
            return Result.toResult(ResultCode.REFER_USER_NOT_EXIST);
        }
        if(!PatternUtil.isVerificationCode(code)){
            return Result.toResult(ResultCode.SMS_CHECK_ERROR);
        }
        if(!PatternUtil.isDigitalAndWord(userPassword)){
            return Result.toResult(ResultCode.USER_LOGIN_ERROR);
        }
        if(!PatternUtil.isTradePwd(orderPassword)){
            return Result.toResult(ResultCode.USER_LOGIN_ERROR);
        }
        try {
            return usersBiz.register(phone, userPassword, referPhone, codeId, code, orderPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.toResult(ResultCode.REGISTER_ERROR);
    }
}
