package com.zh.module.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Pets implements Serializable {
    private static final long serialVersionUID = -3547475199239531124L;

    private Integer id;

    private String name;

    private Byte seque;

    private Byte level;

    private BigDecimal priceMin;

    private BigDecimal priceMix;

    private BigDecimal appointmentAmount;

    private BigDecimal payAmount;

    private Integer profitDays;

    private BigDecimal profitRate;

    private String profitCoin;

    private String profitCoinRate;

    private String imgUrl;

    private Byte upgradeId;

    private String startTime;

    private String endTime;

    private Byte state;

    private Date createTime;

    private Date updateTime;

    public static String getDistribution(Integer level){
        return "DISTRIBUTION_NUMBER_" + level;
    }
    public static String getInterval(Integer level){
        return "PETS_BUYS_INTERVAL_" + level;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", seque=").append(seque);
        sb.append(", level=").append(level);
        sb.append(", priceMin=").append(priceMin);
        sb.append(", priceMix=").append(priceMix);
        sb.append(", appointmentAmount=").append(appointmentAmount);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", profitDays=").append(profitDays);
        sb.append(", profitRate=").append(profitRate);
        sb.append(", profitCoin=").append(profitCoin);
        sb.append(", profitCoinRate=").append(profitCoinRate);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", upgradeId=").append(upgradeId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}