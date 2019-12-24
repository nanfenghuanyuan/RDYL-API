package com.zh.module.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getSeque() {
        return seque;
    }

    public void setSeque(Byte seque) {
        this.seque = seque;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public BigDecimal getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(BigDecimal priceMin) {
        this.priceMin = priceMin;
    }

    public BigDecimal getPriceMix() {
        return priceMix;
    }

    public void setPriceMix(BigDecimal priceMix) {
        this.priceMix = priceMix;
    }

    public BigDecimal getAppointmentAmount() {
        return appointmentAmount;
    }

    public void setAppointmentAmount(BigDecimal appointmentAmount) {
        this.appointmentAmount = appointmentAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getProfitDays() {
        return profitDays;
    }

    public void setProfitDays(Integer profitDays) {
        this.profitDays = profitDays;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public String getProfitCoin() {
        return profitCoin;
    }

    public void setProfitCoin(String profitCoin) {
        this.profitCoin = profitCoin == null ? null : profitCoin.trim();
    }

    public String getProfitCoinRate() {
        return profitCoinRate;
    }

    public void setProfitCoinRate(String profitCoinRate) {
        this.profitCoinRate = profitCoinRate == null ? null : profitCoinRate.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Byte getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(Byte upgradeId) {
        this.upgradeId = upgradeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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