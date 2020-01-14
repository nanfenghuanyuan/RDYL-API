package com.zh.module.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.annotation.UserLoginToken;
import com.zh.module.biz.UsersBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.PatternUtil;
import com.zh.module.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-10-17 18:53
 */
@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UsersBiz usersBiz;

    @PostMapping(value = "register", produces = { "application/json;charset=UTF-8"})
    public String register(@RequestBody String param){
        JSONObject params = JSONObject.parseObject(param);
        String phone = params.getString("phone");
        String password = params.getString("password");
        String uuid = params.getString("uuid");
        String code = params.getString("code");
        Integer codeId = params.getInteger("codeId");
        /*参数校验*/
        if(StrUtils.isBlank(phone) || StrUtils.isBlank(code) || codeId == null || StrUtils.isBlank(password)){
            return Result.toResult(ResultCode.PARAM_IS_BLANK);
        }
        /*正则校验*/
        if(phone.length() != 11){
            return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
        }
        //推荐人id校验
        if(uuid.length() != 8){
            return Result.toResult(ResultCode.REFERPHONE_TYPE_ERROR);
        }
        if(!PatternUtil.isVerificationCode(code)){
            return Result.toResult(ResultCode.SMS_CHECK_ERROR);
        }
        if(!PatternUtil.isDigitalAndWord(password)){
            return Result.toResult(ResultCode.USER_PWD_TYPE_ERROR);
        }
        try {
            return usersBiz.register(phone, password, uuid, codeId, code);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @PostMapping(value = "/login", produces = { "application/json;charset=UTF-8"})
    public Object login(@RequestBody Users user){
        try {
            return usersBiz.login(user);
        } catch (Exception e) {
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    @PostMapping(value = "/getAuthState")
    public String getAuthState(@CurrentUser Users user){
        try {
            return usersBiz.getAuthState(user);
        } catch (Exception e) {
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 修改密码
     * @param user
     * @param param
     * @return
     */
    @PostMapping(value = "/updatePassword", produces = { "application/json;charset=UTF-8"})
    public Object updatePassword(@CurrentUser Users user, @RequestBody String param){
        try {
            JSONObject params = JSON.parseObject(param);
            String password = params.getString("password");
            String code = params.getString("code");
            Integer codeId = params.getInteger("codeId");
            if(StrUtils.isBlank(password) || StrUtils.isBlank(code) || codeId == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if(!PatternUtil.isDigitalAndWord(password)){
                return Result.toResult(ResultCode.USER_PWD_TYPE_ERROR);
            }
            if(!PatternUtil.isVerificationCode(code)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }
            return usersBiz.updatePassword(user, password, code, codeId);
        } catch (Exception e) {
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 修改交易密码
     * @param user
     * @param param
     * @return
     */
    @PostMapping(value = "/updateOrderPassword", produces = { "application/json;charset=UTF-8"})
    public Object updateOrderPassword(@CurrentUser Users user, @RequestBody String param){
        try {
            JSONObject params = JSON.parseObject(param);
            String password = params.getString("password");
            String code = params.getString("code");
            Integer codeId = params.getInteger("codeId");
            if(StrUtils.isBlank(password) || StrUtils.isBlank(code) || codeId == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if(!PatternUtil.isTradePwd(password)){
                return Result.toResult(ResultCode.USER_PWD_TYPE_ERROR);
            }
            if(!PatternUtil.isVerificationCode(code)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }
            return usersBiz.updateOrderPassword(user, password, code, codeId);
        } catch (Exception e) {
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @GetMapping("/getMessage")
    public String getMessage(@CurrentUser Users users){
        return Result.toResult(ResultCode.SUCCESS, JSONObject.toJSON(users))    ;
    }



}
