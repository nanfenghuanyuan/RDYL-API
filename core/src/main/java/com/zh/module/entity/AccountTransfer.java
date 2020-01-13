package com.zh.module.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountTransfer implements Serializable {
    private static final long serialVersionUID = 4152934345269546036L;

    private Integer id;

    private Integer userId;

    private Integer toUserId;

    private Byte coinType;

    private Byte accountType;

    private BigDecimal amount;

    private Integer relatedid;

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

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Byte getCoinType() {
        return coinType;
    }

    public void setCoinType(Byte coinType) {
        this.coinType = coinType;
    }

    public Byte getAccountType() {
        return accountType;
    }

    public void setAccountType(Byte accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getRelatedid() {
        return relatedid;
    }

    public void setRelatedid(Integer relatedid) {
        this.relatedid = relatedid;
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
        sb.append(", toUserId=").append(toUserId);
        sb.append(", coinType=").append(coinType);
        sb.append(", accountType=").append(accountType);
        sb.append(", amount=").append(amount);
        sb.append(", relatedid=").append(relatedid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}