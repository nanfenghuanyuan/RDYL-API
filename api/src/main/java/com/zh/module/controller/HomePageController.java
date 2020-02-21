package com.zh.module.controller;

import com.zh.module.annotation.CurrentUser;
import com.zh.module.annotation.UserLoginToken;
import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.SystemBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-10-17 18:53
 */
@RestController
@RequestMapping(value = "home")
public class HomePageController {

    @Autowired
    private HomeBiz homeBiz;

    @ResponseBody
    @RequestMapping(value="init",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String init(@CurrentUser Users users){
        try {
            return homeBiz.init(users);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    @ResponseBody
    @RequestMapping(value="{level}",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String list(@CurrentUser Users users, @PathVariable("level") Integer level){
        try {

            return homeBiz.get(users, level);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
