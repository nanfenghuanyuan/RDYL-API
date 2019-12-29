package com.zh.module.controller;

import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.AccountBiz;
import com.zh.module.biz.HomeBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-10-17 18:53
 */
@RestController
@RequestMapping(value = "wallet")
public class AccountController {

    @Autowired
    private AccountBiz accountBiz;

    @ResponseBody
    @RequestMapping(value="init",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String init(@CurrentUser Users users){
        try {
            return accountBiz.init(users);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
