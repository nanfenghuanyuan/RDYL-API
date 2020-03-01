package com.zh.module.biz;

import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;

import java.text.ParseException;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface PetsV2Biz {

    String buy(Users users, Integer level) throws ParseException;
}
