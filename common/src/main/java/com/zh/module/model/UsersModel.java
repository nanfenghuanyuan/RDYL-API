package com.zh.module.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-23 20:03
 **/
@Data
public class UsersModel {
    /**
     * id
     */
    private Integer id;
    /**
     * 昵称
     */
    private String nickName;
    private String account;
    /**
     * 手机号
     */
    private String phone;

    private String uuid;
    /**
     * 状态 0未激活 1已激活 2冻结 3拉黑
     */
    private Integer state;
    /**
     * 贡献值
     */
    private Integer contribution;
}
