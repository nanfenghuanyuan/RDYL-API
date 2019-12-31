package com.zh.module.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-31 16:34
 **/
@Data
public class CoinModule {
    private Integer id;
    private String name;
    private String address;
    private String rechargeUrl;
    private BigDecimal rechargeFee;
    private BigDecimal withdrawFee;
    private BigDecimal rechargeAmountMin;
    private BigDecimal withdrawAmountMin;
    private String rechargeDoc;
    private String withdrawDoc;
}
