package com.zh.module.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PetsMatchingList implements Serializable {
    private static final long serialVersionUID = -3559740004928792305L;

    private Integer id;

    private Byte petId;

    private Byte level;

    private Byte buyUserId;

    private Byte saleUserId;

    private BigDecimal amount;

    private Byte state;

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

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Byte getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(Byte buyUserId) {
        this.buyUserId = buyUserId;
    }

    public Byte getSaleUserId() {
        return saleUserId;
    }

    public void setSaleUserId(Byte saleUserId) {
        this.saleUserId = saleUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        sb.append(", petId=").append(petId);
        sb.append(", level=").append(level);
        sb.append(", buyUserId=").append(buyUserId);
        sb.append(", saleUserId=").append(saleUserId);
        sb.append(", amount=").append(amount);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}