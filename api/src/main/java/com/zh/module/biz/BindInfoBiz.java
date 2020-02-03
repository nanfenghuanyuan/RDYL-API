package com.zh.module.biz;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.entity.Users;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface BindInfoBiz {
    /**
     * 获取绑定信息
     * @param users
     * @return
     */
    String getBindInfo(Users users);

    /**
     * 绑定
     * @param users
     * @param params
     * @return
     */
    String binding(Users users, JSONObject params);

    /**
     * 取消绑定
     * @param users
     * @param params
     * @return
     */
    String cancel(Users users, JSONObject params);

    String get(Users users, Integer type);
}
