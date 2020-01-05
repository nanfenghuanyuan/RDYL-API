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
public class TeamListModel {
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 操作
     */
    private String phone;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 认证状态
     */
    private Integer idStatus;
}
