package com.zh.module.enums;


public enum ResultCode {
	/* 成功状态码 */
    SUCCESS(10000, "成功"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "输入的数据有误，请重新输入"),
    PARAM_IS_BLANK(10002, "请把信息填写完整"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "用户名或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被冻结"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),
    USER_ACCOUNT_LOGOFF(20006, "账号已被注销"),
    USER_NOT_REALNAME(20007, "请先完成实名认证"),
    USER_REALNAME_ERROR(20008, "用户当前实名状态不可用"),
    USER_PWD_TYPE_ERROR(20009, "密码格式不正确，清输入6-18位数字和字母的组合"),
    USER_ORDER_PWD_TYPE_ERROR(20009, "密码格式不正确，请输入6位数字的交易密码"),
    USER_OLD_PASSWORD_ERROR(20010, "旧密码错误"),
    USER_STATE_ERROR(20011, "账号当前状态不可用，请先激活"),
    USER_IDSTATE_ERROR(20012, "姓名与实名认证的一致，请重新输入"),

    /* 业务错误：30001-39999 */
    SMS_INTERFACE_ERROR(30001, "短信接口异常"),
    SMS_FREQUENT_SEND(30002, "手机号频繁限制"),
    SMS_CHECK_ERROR(30003, "验证码错误"),
    SMS_TIME_LIMIT_ERROR(30004, "验证码已过期"),
    SMS_COUNTS_LIMIT_ERROR(30005, "验证码使用超出次数限制"),
    REFER_USER_NOT_EXIST(30006, "推荐人账号不存在"),
    REFER_USER_ACCOUNT_FORBIDDEN(30007, "推荐人账号已被冻结"),
    REFER_USER_ACCOUNT_LOGOFF(30008, "推荐人账号已被注销"),
    ORDERNUM_HAS_EXISTED(30009, "订单号已存在"),
    CARDID_NOT_EXISTED(30010, "银行卡不存在"),
    CARD_DELETE_FAIL(30011, "银行卡删除失败"),
    AMOUNT_ERROR(30012, "金额输入不正确"),
    ORDERPWD_IN_LOCK(30013, "交易密码错误次数超限，%s分钟后再试。若忘记密码，请联系客服进行重置处理!"),
    ORDERPWD_ERROR(30014, "交易密码错误，您还有%s次机会"),
    AMOUNT_NOT_ENOUGH(30015, "账户余额不足"),
    AMOUNT_NOT_ENOUGH_REAL_NAME_REWARD(30106, "余额不足以提现，请保证在转出后资金大于系统奖励金额。系统奖励金额在%s次交易挖矿后解冻。"),
    TO_USER_NOT_EXIST(30016, "对方用户不存在"),
    TO_USER_FORBIDDEN(30017, "对方账号已被冻结"),
    TO_USER_LOGOFF(30018, "对方账号已被注销"),
    TO_USER_NOT_REALNAME(30019, "对方用户未实名"),
    WITHDRAW_COUNT_LIMINT(30020, "日提现次数超出限制"),
    WITHDRAW_SUM_LIMIT(30021, "日提现金额超出限制"),
    TO_USER_NOT_ONESELF(30022, "不能给自己转账"),
    TO_USER_ACCOUNT_NOT_EXIST(30023, "对方钱包信息不存在"),
    OLD_PASSWORD_ERROR(30024, "旧密码错误"),
    ORDERPWD_NOT_EXISITED(30025, "未设置交易密码"),
    FILE_TYPE_ERROR(30026, "文件类型错误"),
    FILE_UPLOAD_ERROR(30027, "文件上传失败"),
    ORDER_STATE_INACTIVE(30028, "订单状态不合法"),
    ALIPAY_HAS_EXIST(30029,"支付宝账号已绑定"),
    ORDERPWD_HAS_EXIST(30030, "交易密码已设置"),
    FEE_USER_NOT_EXIST(30031, "平台用户不存在"),
    REFERPHONE_TYPE_ERROR(30032, "推荐人id不正确"),
    COUNT_BEYOND_LIMIT(30033, "档位超出最大限制"),
    DIG_RECHARGE_INACTIVE(30034, "挖火蚁服务已失效"),
    REAL_NAME_FINISHED(30035, "用户已完成实名认证"),
    REAL_NAME_INIT_FAIL(30036, "实名认证初始化失败"),
    REAL_NAME_TASK_NOT_EXIST(30037, "未进行实人认证"),
    REAL_NAME_ING(30038, "认证中"),
    REAL_NAME_FAIL(30039, "实名认证失败"),
    REAL_NAME_AGE_ILLEGAL(30040, "实名认年龄不合法"),
    REAL_NAME_IDCARD_EXIST(30041, "身份证号已经在其他账号认证"),
    REAL_NAME_LIMIT(30042, "实名认证限制中"),
    REGISTER_ERROR(30043, "注册失败"),
    SMS_ERROR(30044, "验证码获取失败"),
    TIME_ERROR(30045, "不在允许的时间范围内"),
    MATCHING_IS_ALIVE(30046, "不要重复预约"),
    PHONE_TYPE_ERROR(30047, "手机号格式不正确"),
    PETS_HAS_NONE(30048, "来晚一步哦，宠物已经被抢购一空了"),
    PETS_STATE_ERROR(30049, "宠物当前状态不可进行此操作哦"),
    OPERATOR_NOT_LIMIT(30050, "无权进行当前操作"),
    TRANS_ROLE(30051, "仅可上下级之间进行转账"),
    BIND_INFO_NONE(30052, "请先完成支付信息绑定"),


    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统异常"),
    SYSTEM_PARAM_ERROR(40002, "系统参数未配置"),

    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_ALREADY_EXISTED(50002, "数据已存在"),
    UPDATE_DATA_NOT_EXIST(50003, "数据无更新"),

    /* 接口错误：60001-69999 */
    INTERFACE_SIGN_ERROR(60001,"接口签名认证错误"),
    INTERFACE_DECRYPT_ERROR(60002,"接口解密失败"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),
    OKCOIN_INTERFACE_ERROR(61001, "OKCoin接口返回出错"),
    RONGCLOUD_INTERFACE_ERROR(62001, "融云接口返回出错"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "功能已关闭"),
    ODIN_NO_ACCESS(70004, "本期已售罄，请期待下一期"),
    PERMISSION_REGISTER_NO_ACCESS(70002, "注册功能已关闭"),
    PERMISSION_NO_OPEN(70003, "功能暂未开放"),

    VCODE_FALSE(70004, "注册验证码错误");

    private Integer code;

    private String msg;
    
    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.msg;
    }
    
    public void setMessage(String msg){
    	this.msg = msg;
    }
    
    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.msg;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
    


}
