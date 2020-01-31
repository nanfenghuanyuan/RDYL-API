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
public class TeamListModel {

    private String phone;
    private String name;
    private String time;
    /**
     * 认证状态 0待激活 1已激活
     */
    private Integer idStatus;
}
