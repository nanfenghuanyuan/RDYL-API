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
	//待捕捉宠物列表 level 阶段
	public static final String PETS_LIST_WAIT_APPOINTMENT = "rdyl:waitAppointment:%s";

}
