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
public class AppoinModel {
    /**
     * 数量
     */
    private String amount;
    /**
     * 操作
     */
    private String name;
    /**
     * 时间
     */
    private String time;
}
