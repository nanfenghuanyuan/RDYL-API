package com.zh.module.constants;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class GlobalParams {

    public static final int DEFAULT_PETS_ID = -1; //无具体指向

    public static final int INACTIVE = 0; //未激活
    public static final int ACTIVE = 1; //激活
    public static final int FORBIDDEN = 2; //冻结
    public static final int LOGOFF = 3; //注销

    public static final int SMS_CODE_REGIESTER = 0;//注册短信
    public static final int SMS_CODE_OTHER = 1;//业务短信

    public static final int WITHDRAW_NO_PAY = 0;//提现未处理
    public static final int WITHDRAW_PAY = 1;//提现已处理

    public static final int ORDER_STATE_UNTREATED = 0;//正在处理
    public static final int ORDER_STATE_TREATED = 1;//已成功
    public static final int ORDER_STATE_BACK = 2;//已撤销
    public static final int ORDER_STATE_FAIL = 3;//已失败

    public static  final int PAY_ALIPAY=0;//支付宝
    public static  final int PAY_WECHANT=1;//微信
    public static  final int PAY_BANK=2;//银行卡
    public static  final int PAY_TOKEN=3;//虚拟币
    public static final int TRANSFER_TYPE_IN = 0;//转入
    public static final int TRANSFER_TYPE_OUT = 1;//转出

    public static final int COMMISSION_TYPE_PERFORM = 0;//平台手续费
    public static final int COMMISSION_TYPE_REFER = 1;//推荐人手续费


    public static final int REALNAME_STATE_ING = 0;//未进行实名认证
    public static final int REALNAME_STATE_SUCCESS = 1;//实名认证成功
    public static final int REALNAME_STATE_FAIL = 2;//实名认证失败

    public static final int REALNAME_NEW_STATE_NO = 0;//未实名
    public static final int REALNAME_NEW_STATE_TRUE = 1;//已通过

    public static final int REALNAME_STATE_FALSE = 0;//失败
    public static final int REALNAME_STATE_SUCCESSS = 1;//成功
    public static final int REALNAME_STATE_WAIT = 2;//待验证


    public static final int COMMISSION_TYPE_LOGIN = 0;//登录奖励
    public static final int COMMISSION_TYPE_REALNAME = 1;//实名奖励


    public static final int ON = 1;//开关开启
    public static final int OFF = 0;//开关关闭

    public static final int SEX_MALE = 0;//男
    public static final int SEX_FAMALE = 1;//女

    public static final int APPLY_STATE_WAITINT_PROCESS = 0;//等待处理
    public static final int APPLY_STATE_SUCCESS = 1;//已成功
    public static final int APPLY_STATE_FAIL = 2;//已拒绝

    public static  final  int RECHARGE_STATE_NEW = 0;//充值未处理
    public static  final  int RECHARGE_STATE_FINISH = 1;//充值完成
    public static  final  int RECHARGE_STATE_CANCEL = 2;//充值撤销

    /**
     * 首页宠物状态 可预约
     */
    public static  final  int PET_STATE_0 = 0;
    /**
     * 首页宠物状态 已预约
     */
    public static  final  int PET_STATE_1 = 1;
    /**
     * 首页宠物状态 可领养
     */
    public static  final  int PET_STATE_2 = 2;
    /**
     * 首页宠物状态 待领养
     */
    public static  final  int PET_STATE_3 = 3;
    /**
     * 首页宠物状态 已领养
     */
    public static  final  int PET_STATE_4 = 4;
    /**
     * 首页宠物状态 成长中
     */
    public static  final  int PET_STATE_5 = 5;
    /**
     * 停用
     */
    public static  final  int PET_STATE_6 = 6;

    /**
     * 宠物匹配状态 已预约
     */
    public static  final  int PET_MATCHING_STATE_APPOINTMENTING = 0;
    /**
     * 宠物匹配状态 未付款
     */
    public static  final  int PET_MATCHING_STATE_NOPAY = 1;
    /**
     * 宠物匹配状态 未确认
     */
    public static  final  int PET_MATCHING_STATE_PAYED = 2;
    /**
     * 宠物匹配状态 已完成
     */
    public static  final  int PET_MATCHING_STATE_COMPLIETE = 3;
    /**
     * 宠物匹配状态 已取消
     */
    public static  final  int PET_MATCHING_STATE_CANCEL = 4;
    /**
     * 已结束（卖家留存)
     */
    public static  final  int PET_MATCHING_STATE_OVER = 5;

    /**
     * 宠物信息 收益中
     */
    public static  final  int PET_LIST_STATE_PROFITING = 0;
    /**
     * 宠物信息 待转让
     */
    public static  final  int PET_LIST_STATE_WAIT = 1;
    /**
     * 宠物信息 转让中
     */
    public static  final  int PET_LIST_STATE_WAITING = 2;

    /**
     * 个人收益
     */
    public static  final  int PROFIT_RECORD_PERSON = 0;
    /**
     * 动态收益
     */
    public static  final  int PROFIT_RECORD_TEAM = 1;

    /**
     * 个人推荐等级
     */
    public static final int PERSON_LEVEL_0 = 0;
    public static final int PERSON_LEVEL_1 = 1;
    public static final int PERSON_LEVEL_2 = 2;

    /**
     * 订单按钮
     */
    public static final int ORDER_BTN_TYPE_NOPAY = 0;
    public static final int ORDER_BTN_TYPE_CONFIRM = 1;
    public static final int ORDER_BTN_TYPE_WAIT_CONFIRM = 2;


}
