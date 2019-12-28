package com.zh.module.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 领养记录model
 */

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-23 20:03
 **/
@Data
public class PetsMatchingListModel {
    /**
     * 宠物名称
     */
    private String name;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 时间
     */
    private String time;
    private String endTime;
    /**
     * 图片url
     */
    private String imgUrl;
    /**
     * 合约收益
     */
    private String profit;
    /**
     * 已获得收益
     */
    private BigDecimal profited;
    /**
     * 状态 0预约中 1未付款 2未确认 3已完成 4已取消
     */
    private Integer state;
}
