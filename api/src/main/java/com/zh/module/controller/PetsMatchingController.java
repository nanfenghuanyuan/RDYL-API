package com.zh.module.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.PetsMatchingListBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-10-17 18:53
 */
@RestController
@RequestMapping(value = "petsMatchingList")
public class PetsMatchingController {

    @Autowired
    private PetsMatchingListBiz petsMatchingListBiz;

    /**
     * 获取领养列表
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String init(@CurrentUser Users users, Integer rows, Integer page, Integer state){
        try {
            //状态 0领养中 1已领养
            if(state == null){
                state = 1;
            }
            if(page == null){
                page = 0;
            }

            PageModel pageModel = new PageModel(page, rows);
            return petsMatchingListBiz.list(users, state, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
