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
     * @param code
     * @param codeId
     * @return
     */
    String transfer(Users user, String phone, String amount, String code, Integer codeId);

    /**
     * 划转入
     * @param phone
     * @param amount
     * @return
     */
    String transferIn(String phone, String amount);
}
