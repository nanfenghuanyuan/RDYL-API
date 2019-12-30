package com.zh.module.biz;

import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface PetsListBiz {
    String list(Users users, Integer state, PageModel pageModel);

    String get(Users users, Integer id);

    String confirmPay(Users users, Integer id, String password);

    String confirmReceipt(Users users, Integer id, String password);

    /**
     * 定时分发收益
     */
    void getProfit();
}
