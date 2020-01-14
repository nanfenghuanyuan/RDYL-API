package com.zh.module.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-23 20:03
 **/
@Data
public class RechargeModel {
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 状态 0未处理 1成功 2失败
     */
    private String state;
    /**
     * 币种
     */
    private String coinType;
    /**
     * 时间
     */
    private String time;

    public String getStates(Integer state){
        switch (state){
            case 0 : return "未处理";
            case 1 : return "充值成功";
            case 2 : return "充值驳回";
            default: return "系统异常";
        }
    }
}
