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
import com.zh.module.utils.StrUtils;
import com.zh.module.utils.ValidateUtils;
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
    public String init(@CurrentUser Users users, Integer rows, Integer page, Integer state){
        try {
            //状态 1待转让 2转让中 3已完成
            if(state == null){
                state = 1;
            }
            if(page == null){
                page = 0;
            }

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
    @RequestMapping(value="/{id}",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String getId(@CurrentUser Users users, @PathVariable("id") Integer id){
        try {
            if(id == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
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
    /**
     * 确认付款
     * @param users
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="confirm-pay",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String confirmPay(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject json = JSONObject.parseObject(param);
            Integer id = json.getInteger("id");
            String password = json.getString("password");
            String imgUrl = json.getString("imgUrl");

            /*参数校验*/
            if(id == null || StrUtils.isBlank(password) || StrUtils.isBlank(imgUrl)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return petsListBiz.confirmPay(users, id, password, imgUrl);
        } catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 确认收款
     * @param users
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="confirm-receipt",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String confirmReceipt(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject json = JSONObject.parseObject(param);
            Integer id = json.getInteger("id");
            String password = json.getString("password");

            /*参数校验*/
            if(id == null || StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return petsListBiz.confirmReceipt(users, id, password);
        } catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 取消订单
     * @param users
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="cancel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String cancel(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject json = JSONObject.parseObject(param);
            Integer id = json.getInteger("id");
            String password = json.getString("password");

            /*参数校验*/
            if(id == null || StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return petsListBiz.cancel(users, id, password);
        } catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * MEPC兑换
     * @param users
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="exchange",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String exchange(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject json = JSONObject.parseObject(param);
            Integer id = json.getInteger("id");
            String amount = json.getString("amount");

            /*参数校验*/
            if(id == null || StrUtils.isBlank(amount)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return petsListBiz.exchange(users, id, amount);
        } catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
