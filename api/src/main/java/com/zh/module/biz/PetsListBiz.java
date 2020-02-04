package com.zh.module.biz;

import com.zh.module.entity.PetsMatchingList;
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

    String confirmPay(Users users, Integer id, String password, String imgUrl) throws Exception;

    String confirmReceipt(Users users, Integer id, String password) throws Exception;

    /**
     * 定时分发收益
     */
    void getProfit();

    /**
     * 取消预约定时
     */
    void cancelAppointmentSchedule();
    /**
     * 取消预约
     */
    void cancelAppointment(Users users, Integer id);

    /**
     * 自动取消未付款订单定时
     */
    void cancelNoPaySchedule();

    /**
     * 自动取消未付款订单
     * @param users
     * @param id
     */
    void cancelNoPay(Users users, Integer id);

    /**
     * 自动确认未确定订单
     * @param users
     */
    void cancelNoConfirm(Users users,  PetsMatchingList petsMatchingList);

    /**
     * 自动确认未确定订单 定时
     */
    void cancelNoConfirmSchedule();

    /**
     * 取消订单
     * @param users
     * @param id
     * @param password
     * @return
     */
    String cancel(Users users, Integer id, String password) throws Exception;

    /**
     * 统计
     */
    void censusAppointment();
}
