package com.zh.module.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Account implements Serializable {
    private static final long serialVersionUID = -2471566361291811948L;

    private Integer id;

    private Integer userId;

    private BigDecimal availbalance;

    private BigDecimal frozenblance;

    private Integer accountType;

    private Integer coinType;

    private Date createtime;

    private Date updatetime;

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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getCoinType() {
        return coinType;
    }

    public void setCoinType(Integer coinType) {
        this.coinType = coinType;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}