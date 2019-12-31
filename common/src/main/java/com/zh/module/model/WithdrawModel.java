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
public class WithdrawModel {
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 状态 0未通过 1已通过
     */
    private Integer state;
    /**
     * 时间
     */
    private String time;
}
