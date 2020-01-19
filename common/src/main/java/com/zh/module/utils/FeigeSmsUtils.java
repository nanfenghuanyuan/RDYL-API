package com.zh.module.utils;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.model.FeiGeSmsResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description: 飞鸽短信
 * @author: zhaohe
 * @create: 2019-10-10 15:17
 */
public class FeigeSmsUtils {

    /**
     * @描述 飞鸽短信发送<br>
     * @param phone 接收短信手机号
     * @return true:成功  false:失败
     * @author administrator
     * @版本 v1.0.0
     * @日期 2019-10-14
     */
    public JSONObject getFeiGeValidateCode(String phone, String templateCode){
        JSONObject json = new JSONObject();
        String code = getCode(6);
        json.put("codes", code);
        FeiGeSmsResponse response = FeigeSmsUtils.sendTemplateSms(phone, code, templateCode);
        if(response == null||!response.getMessage().equals("OK")){
            json.put("code", 416);
            json.put("obj", code);

            return json;
        }
        json.put("code", 200);
        json.put("obj", code);
        return json;
    }
    public FeiGeSmsResponse sendTemplatesSms(String phone, String templateCode, String content){
        return FeigeSmsUtils.sendTemplateSms(phone, content, templateCode);
    }

    /**
     * 发送自定义文本短信
     * @param phone
     * @param content 内容
     */
    public static FeiGeSmsResponse sendTextSms(String phone, String content) {
        FeiGeSmsResponse feiGeSmsResponse = new FeiGeSmsResponse();
        JSONObject jsonObject = new JSONObject();
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                List<BasicNameValuePair> formparams = new ArrayList<>();
                formparams.add(new BasicNameValuePair("Account","18660769100"));
                formparams.add(new BasicNameValuePair("Pwd","ea78a8d62fcb7d7c1fc3e6bda"));//登录后台 首页显示
                formparams.add(new BasicNameValuePair("Content",content));
                formparams.add(new BasicNameValuePair("Mobile",phone));
                formparams.add(new BasicNameValuePair("SignId","221052"));//登录后台 添加签名获取id

                HttpPost httpPost = new HttpPost("http://api.feige.ee/SmsService/Send");
                httpPost.setEntity(new UrlEncodedFormEntity(formparams,"UTF-8"));
                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                jsonObject = JSONObject.parseObject(result);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        feiGeSmsResponse.setSendId(jsonObject.getString("SendId"));
        feiGeSmsResponse.setBlackCount(jsonObject.getString("BlackCount"));
        feiGeSmsResponse.setCode(jsonObject.getString("Code"));
        feiGeSmsResponse.setInvalidCount(jsonObject.getString("InvalidCount"));
        feiGeSmsResponse.setMessage(jsonObject.getString("Message"));
        feiGeSmsResponse.setSuccessCount(jsonObject.getString("SuccessCount"));
        return feiGeSmsResponse;
    }
    /**
     * 发送模板短信
     * @param phone
     * @param content 变量值
     */
    private static FeiGeSmsResponse sendTemplateSms(String phone, String content, String templateId) {
        JSONObject jsonObject = new JSONObject();
        FeiGeSmsResponse feiGeSmsResponse = new FeiGeSmsResponse();
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                List<BasicNameValuePair> formparams = new ArrayList<>();
                formparams.add(new BasicNameValuePair("Account","18660769100"));
                formparams.add(new BasicNameValuePair("Pwd","ea78a8d62fcb7d7c1fc3e6bda"));//登录后台 首页显示
                formparams.add(new BasicNameValuePair("Content",content));
                formparams.add(new BasicNameValuePair("Mobile",phone));
                formparams.add(new BasicNameValuePair("TemplateId",templateId));
                formparams.add(new BasicNameValuePair("SignId","221052"));//登录后台 添加签名获取id

                HttpPost httpPost = new HttpPost("http://api.feige.ee/SmsService/Template");
                httpPost.setEntity(new UrlEncodedFormEntity(formparams,"UTF-8"));
                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                jsonObject = JSONObject.parseObject(result);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        feiGeSmsResponse.setSendId(jsonObject.getString("SendId"));
        feiGeSmsResponse.setBlackCount(jsonObject.getString("BlackCount"));
        feiGeSmsResponse.setCode(jsonObject.getString("Code"));
        feiGeSmsResponse.setInvalidCount(jsonObject.getString("InvalidCount"));
        feiGeSmsResponse.setMessage(jsonObject.getString("Message"));
        feiGeSmsResponse.setSuccessCount(jsonObject.getString("SuccessCount"));
        return feiGeSmsResponse;
    }

    public static void main(String[] args) {
        sendTemplateSms("13165373280", "5555555555", "101756" );
    }

    public static String getCode(Integer length){
        String[] num = new String[]{"0","1","2","3","4","5","6","7","8","9"};
        Random random = new Random();
        String code = "";
        for(int i=0;i<length;i++){
            int index = random.nextInt(num.length);
            code = code+num[index];
        }
        return code;
    }
}
