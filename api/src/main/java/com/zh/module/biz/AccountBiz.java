package com.zh.module.biz;

import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface AccountBiz {
    String init(Users users);

    String transfer(Users users, String phone, String amount, String password);

    String personProfit(Users users, Integer type, PageModel pageModel);
}
