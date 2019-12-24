package com.zh.module.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.UserLoginToken;
import com.zh.module.biz.SystemBiz;
import com.zh.module.biz.UsersBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.utils.PatternUtil;
import com.zh.module.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-10-17 18:53
 */
@RestController
@RequestMapping(value = "sys")
public class SystemController {

    @Autowired
    private SystemBiz systemBiz;
    @ResponseBody
    @RequestMapping(value="config",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String getStartupParam2(){
        try {

            //获取启动参数
            return systemBiz.getStartupParam();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
