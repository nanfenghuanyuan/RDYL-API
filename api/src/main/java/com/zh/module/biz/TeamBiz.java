package com.zh.module.biz;

import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface TeamBiz {
    String init(Users users, Integer type, PageModel pageModel);

    void getProfit();

    /**
     * 定时每天给当天进行交易的宠物增加价值
     */
    void dayProfit();
}
