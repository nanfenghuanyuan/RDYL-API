package com.zh.module.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PetsList implements Serializable {
    private static final long serialVersionUID = 5948430784482313200L;

    private Integer id;

    private Byte petId;

    private BigDecimal price;

    private BigDecimal spend;

    private Byte level;

    private Integer profitDays;

    private BigDecimal profitRate;

    private String profitCoin;

    private String profitCoinRate;

    private String startTime;

    private String endTime;

    private Byte state;

    private Byte sourceFrom;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getPetId() {
        return petId;
    }

    public void setPetId(Byte petId) {
        this.petId = petId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSpend() {
        return spend;
    }

    public void setSpend(BigDecimal spend) {
        this.spend = spend;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
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

    public Byte getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(Byte sourceFrom) {
        this.sourceFrom = sourceFrom;
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
        sb.append(", petId=").append(petId);
        sb.append(", price=").append(price);
        sb.append(", spend=").append(spend);
        sb.append(", level=").append(level);
        sb.append(", profitDays=").append(profitDays);
        sb.append(", profitRate=").append(profitRate);
        sb.append(", profitCoin=").append(profitCoin);
        sb.append(", profitCoinRate=").append(profitCoinRate);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", state=").append(state);
        sb.append(", sourceFrom=").append(sourceFrom);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}