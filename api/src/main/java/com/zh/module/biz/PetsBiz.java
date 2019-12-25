package com.zh.module.biz;

import com.zh.module.entity.Users;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface PetsBiz {
    /**
     * 预约
     * @param users
     * @param level
     * @return
     */
    String appointment(Users users, Integer level);

    /**
     * 领养
     * @param users
     * @param level
     * @return
     */
    String buy(Users users, Integer level);
}
