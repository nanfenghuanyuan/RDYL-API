package com.zh.module.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PetsMatchingList implements Serializable {
    private static final long serialVersionUID = -2310760842580964759L;

    private Integer id;

    private Byte petListId;

    private Byte level;

    private Integer buyUserId;

    private Integer saleUserId;

    private BigDecimal amount;

    private Byte state;

    private String payTime;

    private Date inactiveTime;

    private String appointmentStartTime;

    private String appointmentEndTime;

    private String imgUrl;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getPetListId() {
        return petListId;
    }

    public void setPetListId(Byte petListId) {
        this.petListId = petListId;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Integer getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(Integer buyUserId) {
        this.buyUserId = buyUserId;
    }

    public Integer getSaleUserId() {
        return saleUserId;
    }

    public void setSaleUserId(Integer saleUserId) {
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public Date getInactiveTime() {
        return inactiveTime;
    }

    public void setInactiveTime(Date inactiveTime) {
        this.inactiveTime = inactiveTime;
    }

    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(String appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime == null ? null : appointmentStartTime.trim();
    }

    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(String appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime == null ? null : appointmentEndTime.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
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
        sb.append(", petListId=").append(petListId);
        sb.append(", level=").append(level);
        sb.append(", buyUserId=").append(buyUserId);
        sb.append(", saleUserId=").append(saleUserId);
        sb.append(", amount=").append(amount);
        sb.append(", state=").append(state);
        sb.append(", payTime=").append(payTime);
        sb.append(", inactiveTime=").append(inactiveTime);
        sb.append(", appointmentStartTime=").append(appointmentStartTime);
        sb.append(", appointmentEndTime=").append(appointmentEndTime);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}