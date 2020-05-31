package com.zh.module.biz;


import com.zh.module.entity.Users;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
public interface RpcBiz {

    /**
     * 划转出
     * @param user
     * @param phone
     * @param amount
     * @return
     */
    String transfer(Users user, String phone, String amount, String password) throws Exception;

    /**
     * 划转入
     * @param phone
     * @param amount
     * @return
     */
    String transferIn(String phone, String amount);

    String transferList(Users user, Integer page, Integer rows);
}
