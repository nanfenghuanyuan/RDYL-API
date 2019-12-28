package com.zh.module.biz;

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
}
