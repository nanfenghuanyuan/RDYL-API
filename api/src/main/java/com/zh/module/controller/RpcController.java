package com.zh.module.controller;

import com.alibaba.fastjson.JSONException;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.RpcBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.utils.PatternUtil;
import com.zh.module.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 认购
 * @author: zhaohe
 * @create: 2019-10-17 18:53
 */
@Controller
@RequestMapping(value = "rpc")
public class RpcController {

    @Autowired
    private RpcBiz rpcBiz;
    /**
     * 转让
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value="transfer",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String transfer(@CurrentUser Users user, String amount, String phone, String code, Integer codeId){
        try {
            /*参数校验*/
            if(StrUtils.isBlank(phone) || StrUtils.isBlank(amount) || StrUtils.isBlank(code) || codeId == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            /*正则校验*/
            if(!PatternUtil.isPhone(phone)){
                return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
            }
            return rpcBiz.transfer(user, phone, amount, code, codeId);
        }catch (NumberFormatException | JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 转人
     * @return
     */
    @ResponseBody
    @RequestMapping(value="transferIn",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String transferIn(String amount, String phone){
        try {
            /*参数校验*/
            if(StrUtils.isBlank(phone) || StrUtils.isBlank(amount)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            /*正则校验*/
            if(!PatternUtil.isPhone(phone)){
                return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
            }
            return rpcBiz.transferIn(phone, amount);
        }catch (NumberFormatException | JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
