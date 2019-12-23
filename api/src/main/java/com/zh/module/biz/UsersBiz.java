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
}
