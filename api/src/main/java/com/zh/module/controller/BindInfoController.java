package com.zh.module.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.BindInfoBiz;
import com.zh.module.biz.HomeBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.BindInfo;
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
@RequestMapping(value = "bindInfo")
public class BindInfoController {

    @Autowired
    private BindInfoBiz bindInfoBiz;

    /**
     * 获取绑定信息
     * @param users
     * @return
     */
    @GetMapping(value = "/getBindInfo", produces = { "application/json;charset=UTF-8"})
    public String getBindInfo(@CurrentUser Users users){
        try {
            return bindInfoBiz.getBindInfo(users);
        } catch (Exception e) {
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 绑定信息
     * @param users
     * @return
     */
    @PostMapping(value = "/binding", produces = { "application/json;charset=UTF-8"})
    public String binding(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject params = JSONObject.parseObject(param);
            return bindInfoBiz.binding(users, params);
        } catch (Exception e) {
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 取消绑定信息
     * @param users
     * @return
     */
    @PostMapping(value = "/cancel", produces = { "application/json;charset=UTF-8"})
    public String cancel(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject params = JSONObject.parseObject(param);
            return bindInfoBiz.cancel(users, params);
        } catch (Exception e) {
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 绑定详情
     * @return
     */
    @ResponseBody
    @RequestMapping(value="get",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String list(@CurrentUser Users users, Integer type){
        try {
            if(type == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return bindInfoBiz.get(users, type);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
