package com.zh.module.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.zh.module.encrypt.BASE64;
import com.zh.module.model.FeiGeSmsResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class AliyunSmsUtils {

    private static String key = "TFRBSTRGZjZDZGlQUTN5dXl5OWN6Y1R5";
    private static String secret = "MHRPV1gxSVU5bFpTMWZPMWtISjl6ejY3S1NxWEY0";
        public static String sendSms(String phone, String code) {
            DefaultProfile profile = null;
            try {
                profile = DefaultProfile.getProfile("cn-hangzhou", BASE64.decoder(key), BASE64.decoder(secret));
            } catch (IOException e) {
                e.printStackTrace();
            }
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", "热带雨林");
            request.putQueryParameter("TemplateCode", "SMS_141580455");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", code);
            request.putQueryParameter("TemplateParam", jsonObject.toJSONString());
            try {
                CommonResponse response = client.getCommonResponse(request);
                return response.getData();
            } catch (ClientException e) {
                e.printStackTrace();
            }
            return null;
        }
    /**
     * @描述 飞鸽短信发送<br>
     * @param phone 接收短信手机号
     * @return true:成功  false:失败
     * @author administrator
     * @版本 v1.0.0
     * @日期 2019-10-14
     */
    public JSONObject getValidateCode(String phone, String templateCode){
        JSONObject json = new JSONObject();
        String code = getCode(6);
        json.put("codes", code);
        String result = AliyunSmsUtils.sendSms(phone, code);
        JSONObject jsonObject = new JSONObject();
        String message = jsonObject.getString("Message");
        if(message == null||!message.equals("OK")){
            json.put("code", 416);
            json.put("obj", code);
            return json;
        }
        json.put("code", 200);
        json.put("obj", code);
        return json;
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
