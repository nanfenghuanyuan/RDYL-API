package com.zh.module.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-09-18 14:57
 */
public class UserAuthUtils {
    public static Map<String, Object> idCardAuth(String userName, String idCardNumber) {
        String host = "https://idenauthen.market.alicloudapi.com";
        String path = "/idenAuthentication";
        String method = "POST";
        String appcode = "00000a59d61a4f779ea1c42b824dc38c";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("idNo", idCardNumber);
        bodys.put("name", userName);
        Map<String, Object> map = new HashMap<>();
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            map.put("code", jsonObject.getString("respCode"));
            map.put("msg", jsonObject.getString("respMessage"));
            map.put("name", jsonObject.getString("name"));
            map.put("idCard", jsonObject.getString("idNo"));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
