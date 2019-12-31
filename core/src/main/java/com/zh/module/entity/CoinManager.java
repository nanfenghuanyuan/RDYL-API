package com.zh.module.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CoinManager implements Serializable {
    private static final long serialVersionUID = -6538590407277673885L;

    private Integer id;

    private String name;

    private String address;

    private String rechargeUrl;

    private BigDecimal withdrawFee;

    private BigDecimal rechargeFee;

    private BigDecimal rechargeAmountMin;

    private BigDecimal withdrawAmountMin;

    private String rechargeDoc;

    private String withdrawDoc;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getRechargeUrl() {
        return rechargeUrl;
    }

    public void setRechargeUrl(String rechargeUrl) {
        this.rechargeUrl = rechargeUrl == null ? null : rechargeUrl.trim();
    }

    public BigDecimal getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(BigDecimal withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public BigDecimal getRechargeFee() {
        return rechargeFee;
    }

    public void setRechargeFee(BigDecimal rechargeFee) {
        this.rechargeFee = rechargeFee;
    }

    public BigDecimal getRechargeAmountMin() {
        return rechargeAmountMin;
    }

    public void setRechargeAmountMin(BigDecimal rechargeAmountMin) {
        this.rechargeAmountMin = rechargeAmountMin;
    }

    public BigDecimal getWithdrawAmountMin() {
        return withdrawAmountMin;
    }

    public void setWithdrawAmountMin(BigDecimal withdrawAmountMin) {
        this.withdrawAmountMin = withdrawAmountMin;
    }

    public String getRechargeDoc() {
        return rechargeDoc;
    }

    public void setRechargeDoc(String rechargeDoc) {
        this.rechargeDoc = rechargeDoc == null ? null : rechargeDoc.trim();
    }

    public String getWithdrawDoc() {
        return withdrawDoc;
    }

    public void setWithdrawDoc(String withdrawDoc) {
        this.withdrawDoc = withdrawDoc == null ? null : withdrawDoc.trim();
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
        sb.append(", address=").append(address);
        sb.append(", rechargeUrl=").append(rechargeUrl);
        sb.append(", withdrawFee=").append(withdrawFee);
        sb.append(", rechargeFee=").append(rechargeFee);
        sb.append(", rechargeAmountMin=").append(rechargeAmountMin);
        sb.append(", withdrawAmountMin=").append(withdrawAmountMin);
        sb.append(", rechargeDoc=").append(rechargeDoc);
        sb.append(", withdrawDoc=").append(withdrawDoc);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}