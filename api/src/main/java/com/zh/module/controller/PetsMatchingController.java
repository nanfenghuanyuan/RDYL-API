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

    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String init(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject params = JSONObject.parseObject(param);
            Integer state = params.getInteger("state");
            Integer rows = params.getInteger("rows");
            Integer page = params.getInteger("page");
            //状态 0预约中 1未付款 2未确认 3已完成 4已取消
            if(state == null){
                state = 1;
            }
            if(page == null){
                page = 0;
            }
            page = page + 1;
            PageModel pageModel = new PageModel(page, rows);
            return petsMatchingListBiz.list(users, state, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
