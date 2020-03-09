package com.zh.module.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.AccountBiz;
import com.zh.module.biz.HomeBiz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.exception.BanlanceNotEnoughException;
import com.zh.module.model.PageModel;
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
    public String personProfit(@CurrentUser Users users, Integer rows, Integer page, Integer type){
        try {
            if(type == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if(page == null){
                page = 0;
            }
            PageModel pageModel = new PageModel(page, rows);
            return accountBiz.personProfit(users, type, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 预约记录
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="appointmentRecord",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String appointmentRecord(@CurrentUser Users users, Integer rows, Integer page, Integer coinType){
        try {
            if(page == null){
                page = 0;
            }
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
    @RequestMapping(value="transfer",method=RequestMethod.POST,produces="application/json;charset=utf-8")
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
        }catch (BanlanceNotEnoughException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 提现
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="withdraw",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String withdraw(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject json = JSONObject.parseObject(param);
            String amount = json.getString("amount");
            String password = json.getString("password");
            Integer coinType = CoinType.CNY;
            if(StrUtils.isBlank(amount) || StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return accountBiz.withdraw(users, coinType, amount, password);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 提现列表
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="withdraw/list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String withdrawList(@CurrentUser Users users, Integer rows, Integer page, Integer coinType){
        try {
            coinType = CoinType.CNY;
            if(page == null){
                page = 0;
            }
            PageModel pageModel = new PageModel(page, rows);
            return accountBiz.withdrawList(users, coinType, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 获取余额
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="getAvailBalance",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String getAvailBalance(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject params = JSONObject.parseObject(param);
            byte accountType = AccountType.ACCOUNT_TYPE_ACTIVE;
            Integer coinType = params.getInteger("coinType");
            if(coinType == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return accountBiz.getAvailBalance(users, coinType, accountType);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 获取提现可用余额
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="getWithdrawBalance",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String getWithdrawBalance(@CurrentUser Users users){
        try {
            byte accountType = AccountType.ACCOUNT_TYPE_ACTIVE;
            Integer coinType = CoinType.CNY;
            return accountBiz.getWithdrawBalance(users, coinType, accountType);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 充值
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="recharge",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String recharge(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject json = JSONObject.parseObject(param);
            Integer coinType = json.getInteger("coinType");
            String amount = json.getString("amount");
            String password = json.getString("password");
            String address = json.getString("address");
            if(StrUtils.isBlank(amount) || StrUtils.isBlank(password)|| StrUtils.isBlank(address) || coinType == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if(!PatternUtil.isNumber(amount)){
                return Result.toResult(ResultCode.AMOUNT_ERROR );
            }
            if(address.length() > 100){
                return Result.toResult(ResultCode.PARAM_IS_INVALID );
            }
            return accountBiz.recharge(users, coinType, amount, address, password);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 充值列表
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="recharge/list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String rechargeList(@CurrentUser Users users, Integer rows, Integer page, Integer coinType){
        try {
            if(coinType == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if(page == null){
                page = 0;
            }
            PageModel pageModel = new PageModel(page, rows);
            return accountBiz.rechargeList(users, coinType, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
