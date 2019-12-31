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
     * 备注
     */
    private String remark;
    /**
     * 时间
     */
    private String time;
}
