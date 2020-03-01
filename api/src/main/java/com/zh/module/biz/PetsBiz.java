package com.zh.module.biz;

import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;

import java.text.ParseException;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface PetsBiz {
    /**
     * 预约
     * @param users
     * @param level
     * @return
     */
    String appointment(Users users, Integer level) throws ParseException;

    /**
     * 领养
     * @param users
     * @param level
     * @return
     */
    String buy(Users users, Integer level) throws ParseException;

    /**
     * 我的资产
     * @param users
     * @param pageModel
     * @return
     */
    String list(Users users, PageModel pageModel);

    /**
     * 获取购买信息
     * @param users
     * @param level
     * @return
     */
    String getBuyState(Users users, Integer level);
}
