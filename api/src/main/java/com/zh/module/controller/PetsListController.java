package com.zh.module.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.PetsListBiz;
import com.zh.module.biz.PetsMatchingListBiz;
import com.zh.module.constants.GlobalParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.exception.BanlanceNotEnoughException;
import com.zh.module.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-10-17 18:53
 */
@RestController
@RequestMapping(value = "petsList")
public class PetsListController {

    @Autowired
    private PetsListBiz petsListBiz;

    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String init(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject params = JSONObject.parseObject(param);
            Integer state = params.getInteger("state");
            Integer rows = params.getInteger("rows");
            Integer page = params.getInteger("page");
            //状态 1待转让 2转让中 3已完成
            if(state == null){
                state = 1;
            }
            if(page == null){
                page = 0;
            }
            page = page + 1;
            PageModel pageModel = new PageModel(page, rows);
            return petsListBiz.list(users, state, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 获取订单信息
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String getId(@CurrentUser Users users, @PathVariable Integer id){
        try {
            if(id == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            //只有转让中可查看详情
            if(id != GlobalParams.PET_LIST_STATE_WAITING){
                return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
            }
            return petsListBiz.get(users, id);
        } catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
