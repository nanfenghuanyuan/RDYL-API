package com.zh.module.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Users implements Serializable {
    private static final long serialVersionUID = -2094706037865333813L;

    private Integer id;

    private String account;

    private String phone;

    private String password;

    private String nickName;

    private String orderPwd;

    private Integer contribution;

    private Byte teamLevel;

    private Byte personLevel;

    private String uuid;

    private String referId;

    private Byte state;

    private Byte idStatus;

    private Byte effective;

    private Date createTime;

    private Date updateTime;
    //等级对应日最多提现金额
    public static String getWithdrawLevel(Integer level){
        return "AMOUNT_WITHDRAW_LEVEL_" + level;
    }
    //等级对应提现次数
    public static String getWithdrawNumber(Integer level){
        return "AMOUNT_WITHDRAW_NUMBER_" + level;
    }
    //等级对应提现最大值
    public static String getWithdrawMaxAmount(Integer level){
        return "AMOUNT_WITHDRAW_MAX_AMOUNT_" + level;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", phone=").append(phone);
        sb.append(", password=").append(password);
        sb.append(", nickName=").append(nickName);
        sb.append(", orderPwd=").append(orderPwd);
        sb.append(", contribution=").append(contribution);
        sb.append(", teamLevel=").append(teamLevel);
        sb.append(", personLevel=").append(personLevel);
        sb.append(", uuid=").append(uuid);
        sb.append(", referId=").append(referId);
        sb.append(", state=").append(state);
        sb.append(", idStatus=").append(idStatus);
        sb.append(", effective=").append(effective);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}