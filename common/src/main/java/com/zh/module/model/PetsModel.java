package com.zh.module.model;

import lombok.Data;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-23 20:03
 **/
@Data
public class PetsModel {
    /**
     * 宠物名称
     */
    private String name;
    /**
     * 价格区间
     */
    private String priceSection;
    /**
     * 时间区间
     */
    private String dateSection;
    /**
     * 图片url
     */
    private String imgUrl;
    /**
     * 预约、直购价格
     */
    private String payPrice;
    /**
     * 合约收益
     */
    private String profit;
    /**
     * 状态 0可预约 1已预约 2可领养 3待领养 4已领养 5成长中
     */
    private Integer state;
}
