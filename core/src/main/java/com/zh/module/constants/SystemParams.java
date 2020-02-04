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

	//预约自动取消时间(分钟)
	public static final String PETS_MATCHING_CANCEL_TIME = "PETS_MATCHING_CANCEL_TIME";

	//未付款自动取消时间 （分钟）
	public static final String PETS_MATCHING_NO_PAY_CANCEL_TIME = "PETS_MATCHING_NO_PAY_CANCEL_TIME";
	//未确认自动确认时间 （分钟）
	public static final String PETS_MATCHING_NO_CONFIRM_CANCEL_TIME = "PETS_MATCHING_NO_CONFIRM_CANCEL_TIME";


	
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

    //金币获取说明
    public static final String GOLD_HELP_DOC="GOLD_HELP_DOC";
    //上下级转账是否开启
    public static final String TRANSFER_ROLE="TRANSFER_ROLE";

    //个人直推收益
    public static final String PERSON_AWARD_ONE="PERSON_AWARD_ONE";
    public static final String PERSON_AWARD_TWO="PERSON_AWARD_TWO";

    //团队收益
    public static final String TEAM_AWARD_ONE="TEAM_AWARD_ONE";
    public static final String TEAM_AWARD_TWO="PERSON_AWARD_TWO";
    public static final String TEAM_AWARD_THREE="TEAM_AWARD_THREE";

    //未付款惩罚开关
    public static final String NO_PAY_PUNISH="NO_PAY_PUNISH";
    //未付款次数惩罚
    public static final String NO_PAY_PUNISH_ONE="NO_PAY_PUNISH_ONE";
    public static final String NO_PAY_PUNISH_TWO="NO_PAY_PUNISH_TWO";
    public static final String NO_PAY_PUNISH_THREE="NO_PAY_PUNISH_THREE";

    //注册地址
    public static final String REGIST_URL="REGIST_URL";

    //预约提前时间
    public static final String APPOINTMENT_TIME = "APPOINTMENT_TIME";
    //转币激活所需金币数量
    public static final String TRANSFER_AMOUNT_ACTIVE = "TRANSFER_AMOUNT_ACTIVE";

    //提现开始结束时间
    public static final String WITHDRAW_TIME_LIMIT_START = "WITHDRAW_TIME_LIMIT_START";
    public static final String WITHDRAW_TIME_LIMIT_END = "WITHDRAW_TIME_LIMIT_END";
    //提现开关
    public static final String WITHDRAW_ONOFF = "WITHDRAW_ONOFF";
    //IOS下载地址
    public static final String APP_DOWNLAOD_URL_IOS = "APP_DOWNLAOD_URL_IOS";
    //待领养等待时间
    public static final String WAIT_APPOINTMENT_TIME = "WAIT_APPOINTMENT_TIME";
	//多显示可捕捉时间（秒）
	public static final String CAN_BUY_TIME = "CAN_BUY_TIME";
}
