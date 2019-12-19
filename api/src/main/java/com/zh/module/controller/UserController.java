package com.zh.module.controller;

import com.zh.module.annotation.UserLoginToken;
import com.zh.module.biz.UsersBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.utils.DateUtils;
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

    @PostMapping(value = "register")
    public String register(@RequestBody Users user){
        return usersBiz.register(user);
    }

    @PostMapping("/login")
    public Object login(@RequestBody Users user){
        try {
            return usersBiz.login(user);
        } catch (Exception e) {
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }

}
