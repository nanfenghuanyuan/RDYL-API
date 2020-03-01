package com.zh.module.biz;

import com.zh.module.entity.Users;

import java.text.ParseException;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface PetsV2Biz {

    /**
     * 捕捉
     * @param users
     * @param level
     * @return
     * @throws ParseException
     */
    String buy(Users users, Integer level) throws ParseException;

    /**
     * 匹配定时
     */
    void matching(Integer level);
}
