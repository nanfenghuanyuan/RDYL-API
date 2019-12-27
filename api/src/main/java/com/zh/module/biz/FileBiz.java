package com.zh.module.biz;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.entity.Users;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
public interface FileBiz {

    void IoReadImage(String imgUrl, HttpServletResponse response) throws IOException;
}
