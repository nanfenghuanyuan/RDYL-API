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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping(value="init",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String initRealName(@CurrentUser Users user, String name, String idCard){
		try {

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
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="status",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String getStatus(@CurrentUser Users user , String taskId){
		try {
			/*参数校验*/
			if(StrUtils.isBlank(taskId)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			//设置头像
			return userBiz.getStatus(user, taskId);
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
