package com.zh.module.controller;

import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.TeamBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PageModel;
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
@RequestMapping(value = "team")
public class TeamController {

    @Autowired
    private TeamBiz teamBiz;

    @ResponseBody
    @RequestMapping(value="init",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String init(@CurrentUser Users users, Integer rows, Integer page, Integer type){
        try {
            if(type == null){
                type = 0;
            }
            if(page == null){
                page = 0;
            }

            PageModel pageModel = new PageModel(page, rows);
            return teamBiz.init(users, type);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
