package com.zh.module.biz;

import com.zh.module.entity.Users;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface UsersBiz {
    String login(Users user) throws Exception;

    String register(String phone, String password, String uuid, Integer codeId, String code) throws Exception;

    String updatePassword(Users user, String password, String code, Integer codeId) throws Exception;

    String updateOrderPassword(Users user, String password, String code, Integer codeId) throws Exception;

    /**
     * 实名认证获取token
     * @param user
     * @param name
     * @param idCard
     * @return
     */
    String getToken(Users user, String name, String idCard);

    String getStatus(String code, String h5faceId);
}
