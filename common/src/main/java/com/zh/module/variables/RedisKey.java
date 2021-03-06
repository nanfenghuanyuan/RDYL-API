package com.zh.module.variables;

/**
 * @描述 Redis-Key值声明类<br>
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-10-13
 */
public class RedisKey {

	public static final String TEST="TEST";
	
	public static final String SYSTEM_PARAM = "rdyl:systemParam:%s";
	
	public static final String TABLE_NAME = "rdyl:tableName:%s";

	public static final String ORDER_PASSWORD_ERROR_TIMES = "rdyl:orderPassword:%s:errorTimes"; /*交易密码错误次数限制*/
	public static final String ORDER_PASSWORD_ERROR_TIMESTAMP = "rdyl:orderPassword:%s:errorTimestamp"; /*错误次数累计时间范围*/
	public static final String ORDER_PASSWORD_LOCK_TIMESTAMP = "rdyl:orderPassword:%s:lockTimestamp"; /*锁定时长*/
	public static final String SMS_ERROR_TIMES = "rdyl:smsRecord:%s"; /*验证码错误次数*/

	//首页宠物预定用户  宠物 用户id
	public static final String BUY_APPOINTMENT_USER = "rdyl:petsBuy:appointmentUser:%s:%s";

	//交易超时订单列表名称
    public static final String MATCHING_NOTCONFIRM_KEY_NAME = "rdyl:matching:notconfirmName";
    public static final String MATCHING_NOPAY_KEY_NAME = "rdyl:matching:nopayname";
    //交易超时订单列表:超时分钟数
	public static final String MATHCHING_NOTCONFIRM = "rdyl:matching:notconfirm:%s";
	public static final String MATHCHING_NOPAY = "rdyl:matching:nopay:%s";
	//未付款处罚用户
	public static final String PUNISH_NOPAY = "rdyl:punish:%s";
	//实名认证用户获取的faceid
	public static final String REAL_NAME_USER_OBJECT = "rdyl:realname:%s";
	//待捕捉宠物列表 level
	public static final String PETS_LIST_WAIT_APPOINTMENT = "rdyl:waitAppointment:%s";
	//失败待捕捉宠物列表 level
	public static final String PETS_LIST_WAIT_APPOINTMENT_FALSE = "rdyl:waitAppointment:false:%s";
	//带捕捉宠物数量
	public static final String PETS_LIST_WAIT_APPOINTMENT_AMOUNT = "rdyl:waitAppointment:amount:%s";
	//宠物是否可买标志 存在不可买  等级
	public static final String PETS_LIST_BUY_FLAG = "rdyl:buyflag:%s";
	//用户待捕捉列表 等级 用戶
	public static final String PETS_LIST_BUY_LIST = "rdyl:buy:%s";
	//用户待捕捉提交信息 等级 用戶
	public static final String PETS_LIST_BUYS_LIST = "rdyl:buys:%s:%s";
	//团队信息 类型 userId
	public static final String TEAM_INFO = "rdyl:team:%s:%s";

}
