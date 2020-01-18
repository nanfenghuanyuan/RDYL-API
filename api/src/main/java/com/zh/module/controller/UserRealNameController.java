package com.zh.module.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.UsersBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 用户登录注册
* @author lina 
* @date 2017-12-26
* @version V1.0
 */
@Controller
@RequestMapping("/realname")
public class UserRealNameController{
	@Autowired
	private UsersBiz userBiz;

	/**
	 * 实人认证初始化
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="init",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String initRealName(@CurrentUser Users user, @RequestBody String param){
		try {
			JSONObject jsonObject = JSONObject.parseObject(param);
			String name = jsonObject.getString("name");
			String idCard = jsonObject.getString("idCard");
			if(StrUtils.isBlank(name) || StrUtils.isBlank(idCard)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			return userBiz.getToken(user, name, idCard);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}  catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}


	/**
	 * 获取实人认证信息
	 * @return
	 */
	@RequestMapping(value="status",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String getStatus(Integer userId){
		try {

			return userBiz.getStatus(userId);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}  catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}
}
