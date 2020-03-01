package com.zh.module.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.PetsBiz;
import com.zh.module.biz.PetsV2Biz;
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
@RequestMapping(value = "pets")
public class PetsController {

    @Autowired
    private PetsBiz petsBiz;
    @Autowired
    private PetsV2Biz petsV2Biz;

    /**
     * 预约
     * @param users
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="appointment",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String appointment(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject params = JSONObject.parseObject(param);
            Integer level = params.getInteger("level");
            if(level == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return petsBiz.appointment(users, level);
        } catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 领养
     * @param users
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="buy",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String buy(@CurrentUser Users users, @RequestBody String param){
        try {
            JSONObject params = JSONObject.parseObject(param);
            Integer level = params.getInteger("level");
            if(level == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return petsV2Biz.buy(users, level);
        } catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String init(@CurrentUser Users users, Integer rows, Integer page){
        try {
            if(page == null){
                page = 0;
            }

            PageModel pageModel = new PageModel(page, rows);
            return petsBiz.list(users, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }



}
