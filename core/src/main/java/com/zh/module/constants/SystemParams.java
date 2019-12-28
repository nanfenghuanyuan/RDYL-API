package com.zh.module.constants;


public class SystemParams {
	//短信开关
	public static final String SMS_ONOFF = "SMS_ONOFF";

	//注册功能开关
	public static final String REGIST_ONOFF = "REGIST_ONOFF"; 
	//转账开关
	public static final String TRANSFER_ONOFF = "TRANSFER_ONOFF";
	public static final String TRANSFER_RATE = "TRANSFER_RATE";//转账充值费率

	//短信次数限制
	public static final String SMS_COUNTS_LIMIT = "SMS_COUNTS_LIMIT";
	//短信时间限制   （分钟）
	public static final String SMS_TIME_LIMIT = "SMS_TIME_LIMIT";

	//微信账号、图片
	public static final String WECHAT_ACCOUNT = "WECHAT_ACCOUNT";
	public static final String WECHAT_IMG_URL = "WECHAT_IMG_URL";

	//领养自动取消时间(分钟)
	public static final String PETS_MATCHING_CANCEL_TIME = "PETS_MATCHING_CANCEL_TIME";


	
	
	public static final String ORDERPWD_LOCK_INTERVAL = "ORDERPWD_LOCK_INTERVAL";//交易密码锁定时间(分钟)
	public static final String ORDERPWD_ERROR_INTERVAL = "ORDERPWD_ERROR_INTERVAL";//交易密码错误时间(分钟)
	public static final String ORDERPWD_ERROR_TIMES = "ORDERPWD_ERROR_TIMES";//交易密码错误次数

	
	public static final String SYSTEM_URL = "SYSTEM_URL";//系统地址
	
	public static final String REAL_NAME_ONOFF = "REAL_NAME_ONOFF";//实名认证开关
	public static final String IDCARD_VALIDATE_TIMES_LIMIT = "IDCARD_VALIDATE_TIMES_LIMIT";//身份证验证次数限制
	
	/*APP启动参数配置*/
	public static final String APP_CONFIG_VERSION = "APP_CONFIG_VERSION";//版本号
	public static final String APP_CONFIG_AGREENMENT_URL = "APP_CONFIG_AGREENMENT_URL";//注册协议

	public static final String APP_DOWNLAOD_URL = "APP_DOWNLAOD_URL";//app下载地址

	//默认交易密码
	public static final String ORDER_PASSWORD_DEFAULT="ORDER_PASSWORD_DEFAULT";

	//默认用户密码
	public static final String USER_DEFAULT_PASSWORD="USER_DEFAULT_PASSWORD";

	/*充值*/
    public static final String SYSTEM_RECHARGE_URL="SYSTEM_RECHARGE_URL";

}
