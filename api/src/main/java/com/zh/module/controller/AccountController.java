package com.zh.module.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.AccountBiz;
import com.zh.module.biz.HomeBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PageModel;
import com.zh.module.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 页面初始化
     * @param users
     * @return
     */
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
    /**
     * 个人/动态收益
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="profit",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String personProfit(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject params = JSONObject.parseObject(param);
            Integer rows = params.getInteger("rows");
            Integer page = params.getInteger("page");
            Integer type = params.getInteger("type");
            if(type == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if(page == null){
                page = 0;
            }
            page = page + 1;
            PageModel pageModel = new PageModel(page, rows);
            return accountBiz.personProfit(users, type, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 个人/动态收益
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="appointmentRecord",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String appointmentRecord(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject params = JSONObject.parseObject(param);
            Integer rows = params.getInteger("rows");
            Integer page = params.getInteger("page");
            if(page == null){
                page = 0;
            }
            page = page + 1;
            PageModel pageModel = new PageModel(page, rows);
            return accountBiz.appointmentRecord(users, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 转账
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="transfer",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String transfer(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject json = JSONObject.parseObject(param);
            String phone = json.getString("phone");
            String password = json.getString("password");
            String amount = json.getString("amount");
            if(StrUtils.isBlank(phone) || StrUtils.isBlank(amount) || StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return accountBiz.transfer(users, phone, amount, password);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
