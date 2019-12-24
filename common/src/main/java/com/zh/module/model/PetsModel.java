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
    //宠物名称
    private String name;
    //价格区间
    private String priceSection;
    //图片url
    private String imgUrl;
    //预约、直购价格
    private String payPrice;
    //合约收益
    private String profit;
    //状态 0可预约 1可领养 2待领养 3已领养 4成长中
    private Integer state;
}
