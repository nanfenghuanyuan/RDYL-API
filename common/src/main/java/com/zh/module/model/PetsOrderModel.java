package com.zh.module.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 宠物交易详情
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-23 20:03
 **/
@Data
public class PetsOrderModel {
    private Integer id;
    /**
     * 宠物名称
     */
    private String name;
    /**
     * 价格区间
     */
    private BigDecimal price;
    /**
     * 转让时间
     */
    private String transTime;
    /**
     * 付款时间
     */
    private String payTime;

    /**
     * 合约收益
     */
    private String profit;
    /**
     * 状态 0预约中 1未付款 2未确认 3已完成 4已取消
     */
    private Integer state;

    private String buyPhone;

    private String buyName;

    private String salePhone;

    private String saleName;

    private List<PayInfoModel> payInfoModels;
}
