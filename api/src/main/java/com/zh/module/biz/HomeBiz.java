package com.zh.module.biz;

import com.zh.module.entity.Users;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface HomeBiz {
    String init(Users users);

    String get(Users users, Integer id);
}
