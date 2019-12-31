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

    /**
     * 收益记录 个人/动态
     * @param users
     * @param type
     * @param pageModel
     * @return
     */
    String personProfit(Users users, Integer type, PageModel pageModel);

    /**
     * 预约记录
     * @param users
     * @param pageModel
     * @return
     */
    String appointmentRecord(Users users, PageModel pageModel);

    /**
     * 提现
     * @param users
     * @param coinType
     * @param amount
     * @param password
     * @return
     */
    String withdraw(Users users, Integer coinType, String amount, String password);

    String getAvailBalance(Users users, Integer coinType, byte accountType);

    String withdrawList(Users users, Integer coinType, PageModel pageModel);

    String recharge(Users users, Integer coinType, String amount, String address, String password);

    String rechargeList(Users users, Integer coinType, PageModel pageModel);
}
