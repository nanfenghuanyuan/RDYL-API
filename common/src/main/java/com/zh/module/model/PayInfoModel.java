package com.zh.module.model;

import lombok.Data;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-23 20:03
 **/
@Data
public class PayInfoModel {
    /**
     * 账户
     */
    private String account;
    /**
     * 收款人
     */
    private String name;
    /**
     * 类型 0支付宝 1微信 2银行卡 3token 4备用手机
     */
    private Integer type;

    /**
     * 收款图
     */
    private String imgUrl;

    private String bankName;

    private String branchName;
}
