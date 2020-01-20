package com.zh.module.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.Hashing;
import com.zh.module.utils.HTTP;
import com.zh.module.utils.HttpsUtil;
import com.zh.module.utils.StrUtils;
import org.apache.commons.codec.Charsets;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @program: R.D.Y.LMain
 * @description: 腾讯云
 * @author: zhaohe
 * @create: 2020-01-08 12:50
 **/
@Component
public class TencentCloud {

    private static String appId = "TIDAJjRB";
    private static String secret = "l1So1Bd2uZeIRLQozuxvZ38KKZKgBXFRzEy8LyLdAGFMdAeAUvbYKGJeNS2pqD0V";

    /**
     * 获取url
     * @param userId
     * @param orderNo
     * @return
     */
    public JSONObject getStatus(String userId, String userName, String idCard, String orderNo){
        String h5faceId = getFaceId(orderNo, userName, idCard, userId);
        String accessToken = getAccessToken(appId, secret);
        String nonceTicket = getNONCETicket(appId, accessToken, userId);
        String nonce = StrUtils.getCharAndNumr(32);
        List<String> strings = new LinkedList<>();
        strings.add(appId);
        strings.add(userId);
        strings.add(orderNo);
        strings.add("1.0.0");
        strings.add(h5faceId);
        strings.add(nonce);
        String sign = sign(strings, nonceTicket);
        try {
            JSONObject jsonObject = new JSONObject();
            String url = "http%3a%2f%2f47.56.87.149%3a8081%2frealname%2fstatus%3fuserId%3d" + userId;
            jsonObject.put("url", "https://ida.webank.com/api/web/login?webankAppId=" + appId + "&version=1.0.0&nonce=" + nonce + "&orderNo=" + orderNo + "&h5faceId=" + h5faceId + "&url=" + url +
                      "&resultType=1&userId=" + userId + "&sign=" + sign + "&from=APP");
            jsonObject.put("h5faceId", h5faceId);
            jsonObject.put("orderNo", orderNo);
            jsonObject.put("nonce", nonce);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取认证结果
     * @param orderNo
     * @return
     */
    public JSONObject getResult(String nonce, String orderNo){
        String accessToken = getAccessToken(appId, secret);
        String signTickets = getSignTicket(appId, accessToken);
        List<String> strings = new LinkedList<>();
        strings.add(appId);
        strings.add(orderNo);
        strings.add("1.0.0");
        strings.add(nonce);
        String sign = sign(strings, signTickets);
        try {
            String url = "https://idasc.webank.com/api/server/sync?app_id=%s&nonce=%s&order_no=%s&version=1.0.0&sign=%s";
            String result = HttpsUtil.httpsGet(String.format(url, appId, nonce, orderNo, sign));
            if(!StrUtils.isBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                jsonObject.put("code", jsonObject.getString("code"));
                return jsonObject;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 合作方后台上送身份信息
     * {
     * "code": "0",
     * "msg": "请求成功",
     * "bizSeqNo": "20010820001015300513441638965376",
     * "result": {
     * "bizSeqNo": "20010820001015300513441638965376",
     * "transactionTime": "20200108134416",
     * "orderNo": "1",
     * "h5faceId": "15ccc432808c39eac2cbf11347ed4998",
     * "success": false
     * },
     * "transactionTime": "20200108134416"
     * }
     * @return
     */
    public String getFaceId(String orderNo, String userName, String idCard, String userId){
        String accessToken = getAccessToken(appId, secret);
        String tickets = getSignTicket(appId, accessToken);
        List<String> values = new LinkedList<>();
        values.add(appId);
        values.add(orderNo);
        values.add(userName);
        values.add(idCard);
        values.add(userId);
        values.add("1.0.0");
        String sign = sign(values, tickets);
        Map<String, String> map = new HashMap<>();
        map.put("webankAppId", appId);
        map.put("orderNo", orderNo);
        map.put("name", userName);
        map.put("idNo", idCard);
        map.put("userId", userId);
        map.put("sourcePhotoType", "1");
        map.put("version", "1.0.0");
        map.put("sign", sign);
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            String result = HTTP.postString("https://idasc.webank.com/api/server/h5/geth5faceid", headers, JSONObject.toJSONString(map));
            JSONObject jsonObject = JSONObject.parseObject(result);
            String code = jsonObject.getString("code");
            if(!"0".equals(code)){
                return null;
            }
            return jsonObject.getJSONObject("result").getString("h5faceId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 Access Token
     * {
     * "code": "0",
     * "msg": "请求成功",
     * "bizSeqNo": "20010820001015300812495038830512",
     * "transactionTime": "20200108124950",
     * "success": true,
     * "access_token": "WAA0f-dGGlQHTVClazkxtmW6FX_nRhpUB01QpWs5MbZluhXyEaRVvqVtuyX_KPTjiUKHK_InmWt--TK19eRFV3mCOg",
     * "expire_in": 7200,
     * "expire_time": "20200108144950"
     * }
     */
    public String getAccessToken(String appId, String secret){
        String url = "https://idasc.webank.com/api/oauth2/access_token?app_id=%s&secret=%s&grant_type=client_credential&version=1.0.0";
        try {
            String result = HttpsUtil.httpsGet(String.format(url, appId, secret));
            if(!StrUtils.isBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                String code = jsonObject.getString("code");
                if(!"0".equals(code)){
                    return null;
                }
                return jsonObject.getString("access_token");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 SIGN ticket
     * {
     * "code": "0",
     * "msg": "请求成功",
     * "bizSeqNo": "20010820001015300712591438835645",
     * "transactionTime": "20200108125914",
     * "success": true,
     * "tickets": [
     * {
     * "value": "hABLOAT6InW8AUoyauvUe1jojSWwi6klZMFaEx8YYVghfGt54sWpML58fb64QqMG",
     * "expire_in": 3600,
     * "expire_time": "20200108135914"
     * }
     * ]
     * }
     */
    public String getSignTicket(String appId, String accessToken){
        String url = "https://idasc.webank.com/api/oauth2/api_ticket?app_id=%s&access_token=%s&type=SIGN&version=1.0.0";
        try {
            String result = HttpsUtil.httpsGet(String.format(url, appId, accessToken));
            if(!StrUtils.isBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                String code = jsonObject.getString("code");
                if(!"0".equals(code)){
                    return null;
                }
                JSONArray tickets = jsonObject.getJSONArray("tickets");
                return tickets.getJSONObject(0).getString("value").toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 NONCE ticket
     * @param appId
     * @param accessToken
     * @param userId
     * @return
     */
    public String getNONCETicket(String appId, String accessToken, String userId){
        String url = "https://idasc.webank.com/api/oauth2/api_ticket?app_id=%s&access_token=%s&type=NONCE&version=1.0.0&user_id=%s";
        try {
            url = String.format(url, appId, accessToken, userId);
            String result = HttpsUtil.httpsGet(url);
            if(!StrUtils.isBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                String code = jsonObject.getString("code");
                if(!"0".equals(code)){
                    return null;
                }
                JSONArray tickets = jsonObject.getJSONArray("tickets");
                return tickets.getJSONObject(0).getString("value").toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签名
     * @param values
     * @param ticket
     * @return
     */
    public static String sign(List<String> values, String ticket) {
        if (values == null) {
            throw new NullPointerException("values is null");
        }
        values.removeAll(Collections.singleton(null));
        values.add(ticket);
        java.util.Collections.sort(values);

        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }
        return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
    }

    public static void main(String[] args) {
        TencentCloud tencentCloud = new TencentCloud();
        System.out.println(tencentCloud.getStatus("1", "赵赫", "370883199409167412", "NO1000000"));
    }
}
