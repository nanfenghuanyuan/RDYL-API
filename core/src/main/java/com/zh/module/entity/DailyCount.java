package com.zh.module.entity;

import java.io.Serializable;
import java.util.Date;

public class DailyCount implements Serializable {
    private static final long serialVersionUID = -6691959408050390043L;

    private Integer id;

    private Byte coinType;

    private String dailtConsume;

    private String totalConsume;

    private String dynamicRevenue;

    private String registerNumber;

    private String activationNumber;

    private String realNameNumber;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getCoinType() {
        return coinType;
    }

    public void setCoinType(Byte coinType) {
        this.coinType = coinType;
    }

    public String getDailtConsume() {
        return dailtConsume;
    }

    public void setDailtConsume(String dailtConsume) {
        this.dailtConsume = dailtConsume == null ? null : dailtConsume.trim();
    }

    public String getTotalConsume() {
        return totalConsume;
    }

    public void setTotalConsume(String totalConsume) {
        this.totalConsume = totalConsume == null ? null : totalConsume.trim();
    }

    public String getDynamicRevenue() {
        return dynamicRevenue;
    }

    public void setDynamicRevenue(String dynamicRevenue) {
        this.dynamicRevenue = dynamicRevenue == null ? null : dynamicRevenue.trim();
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber == null ? null : registerNumber.trim();
    }

    public String getActivationNumber() {
        return activationNumber;
    }

    public void setActivationNumber(String activationNumber) {
        this.activationNumber = activationNumber == null ? null : activationNumber.trim();
    }

    public String getRealNameNumber() {
        return realNameNumber;
    }

    public void setRealNameNumber(String realNameNumber) {
        this.realNameNumber = realNameNumber == null ? null : realNameNumber.trim();
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
        sb.append(", coinType=").append(coinType);
        sb.append(", dailtConsume=").append(dailtConsume);
        sb.append(", totalConsume=").append(totalConsume);
        sb.append(", dynamicRevenue=").append(dynamicRevenue);
        sb.append(", registerNumber=").append(registerNumber);
        sb.append(", activationNumber=").append(activationNumber);
        sb.append(", realNameNumber=").append(realNameNumber);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}