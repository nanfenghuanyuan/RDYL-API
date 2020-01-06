package com.zh.module.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Account implements Serializable {
    private static final long serialVersionUID = 3469185435834123359L;

    private Integer id;

    private Integer userId;

    private BigDecimal availbalance;

    private BigDecimal frozenblance;

    private Byte accountType;

    private Byte coinType;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAvailbalance() {
        return availbalance;
    }

    public void setAvailbalance(BigDecimal availbalance) {
        this.availbalance = availbalance;
    }

    public BigDecimal getFrozenblance() {
        return frozenblance;
    }

    public void setFrozenblance(BigDecimal frozenblance) {
        this.frozenblance = frozenblance;
    }

    public Byte getAccountType() {
        return accountType;
    }

    public void setAccountType(Byte accountType) {
        this.accountType = accountType;
    }

    public Byte getCoinType() {
        return coinType;
    }

    public void setCoinType(Byte coinType) {
        this.coinType = coinType;
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
        sb.append(", userId=").append(userId);
        sb.append(", availbalance=").append(availbalance);
        sb.append(", frozenblance=").append(frozenblance);
        sb.append(", accountType=").append(accountType);
        sb.append(", coinType=").append(coinType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}