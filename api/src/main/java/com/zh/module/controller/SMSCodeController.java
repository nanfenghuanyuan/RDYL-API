package com.zh.module.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zh.module.biz.SmsCodeBiz;
import com.zh.module.dto.Result;
import com.zh.module.enums.ResultCode;
import com.zh.module.utils.StrUtils;
import com.zh.module.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 短信验证码
* @author zhaohe
* @date 2018-7-12
* @version V1.0
 */
@Controller
@RequestMapping("/sms")
public class SMSCodeController{
	@Autowired
	SmsCodeBiz smsCodeBiz;

	/**
	 * @Description: 获取短信验证验证码
	 * @param  params 参数列表
	 * @return String
	 * @date 2017-8-8
	 * @author lina
	 */
	@ResponseBody
	@RequestMapping(value="get",method= RequestMethod.POST ,produces = "application/json;charset=utf-8")
	public String getValidateCode(@RequestBody String params){
		try {
			JSONObject json = JSONObject.parseObject(params);
			String phone = json.getString("phone");
			Integer type = json.getInteger("type");
			//参数校验
			if(StrUtils.isBlank(phone) || type == null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isPhone(phone)){
				return Result.toResult(ResultCode.PARAM_IS_INVALID);
			}
			//获取校验码
			return smsCodeBiz.getValidateCode(phone, type);
		}catch (NumberFormatException | JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}

	}
}
