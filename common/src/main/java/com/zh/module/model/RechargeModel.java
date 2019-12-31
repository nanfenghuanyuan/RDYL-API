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
public class RechargeModel {
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 币种
     */
    private Integer coinType;
    /**
     * 时间
     */
    private String time;
}
