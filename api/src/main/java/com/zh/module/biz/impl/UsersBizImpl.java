package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zh.module.biz.UsersBiz;
import com.zh.module.dto.Result;
import com.zh.module.encrypt.MD5;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
public class UsersBizImpl implements UsersBiz {
    @Autowired
    private UsersService usersService;
    @Override
    public String login(Users user) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Users userForBase = usersService.login(user);
        if(userForBase == null){
            return Result.toResult(ResultCode.USER_NOT_EXIST);
        }else {
            if (!userForBase.getPassword().equals(MD5.getMd5(user.getPassword()))){
                return Result.toResult(ResultCode.USER_LOGIN_ERROR);
            }else {
                String token = getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return Result.toResult(ResultCode.SUCCESS, jsonObject);
            }
        }
    }

    @Override
    public String register(Users user) {
        return null;
    }

    public String getToken(Users user) {
        String token = "";
        // 将 user id 保存到 token 里面
        token= JWT.create().withAudience(user.getId().toString())
                // 以 password 作为 token 的密钥
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
