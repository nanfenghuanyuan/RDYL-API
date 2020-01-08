package com.zh.module.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cloudauth.model.v20180916.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.zh.module.constants.GlobalParams;
import com.zh.module.entity.IdcardValidate;
import org.springframework.stereotype.Component;

import java.lang.management.GarbageCollectorMXBean;
import java.util.UUID;

@Component
public class H5RpBasic {
    public JSONObject init(String userName, String idCard){
        JSONObject jsonObject = new JSONObject();
        //创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",             //默认
                "LTAIwwPb2BAVB2wu",         //您的Access Key ID
                "SGJYBHkH8MP4ocBcMehUCLjW1O9ivS");    //您的Access Key Secret
        IAcsClient client = new DefaultAcsClient(profile);
        String biz = "222"; //您在控制台上创建的、采用RPH5BioOnly认证方案的认证场景标识, 创建方法：https://help.aliyun.com/document_detail/59975.html
        String ticketId = UUID.randomUUID().toString(); //认证ID, 由使用方指定, 发起不同的认证任务需要更换不同的认证ID
        String token = null; //认证token, 表达一次认证会话
        int statusCode = -1; //-1 未认证, 0 认证中, 1 认证通过, 2 认证不通过
//1. 服务端发起认证请求, 获取到token
//GetVerifyToken接口文档：https://help.aliyun.com/document_detail/57050.html
        GetVerifyTokenRequest getVerifyTokenRequest = new GetVerifyTokenRequest();
        getVerifyTokenRequest.setBiz(biz);
        getVerifyTokenRequest.setTicketId(ticketId);
        getVerifyTokenRequest.setMethod(MethodType.POST);
//通过binding参数传入业务已经采集的认证资料，其中姓名、身份证号为必要字段
//若需要binding图片资料，请控制单张图片大小在 2M 内，避免拉取超时
        getVerifyTokenRequest.setBinding("{\"Name\": \"" + userName + "\",\"IdentificationNumber\": \"" + idCard + "\"}");
        GetVerifyTokenResponse response = new GetVerifyTokenResponse();
        try {
            response = client.getAcsResponse(getVerifyTokenRequest);
            token = response.getData().getVerifyToken().getToken(); //token默认30分钟时效，每次发起认证时都必须实时获取
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        jsonObject.put("token", token);
        jsonObject.put("url", response.getData().getCloudauthPageUrl());
        jsonObject.put("ticketId", ticketId);
        return jsonObject;
    }

    public JSONObject getStatus(String ticketId){
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",             //默认
                "LTAIwwPb2BAVB2wu",         //您的Access Key ID
                "SGJYBHkH8MP4ocBcMehUCLjW1O9ivS");    //您的Access Key Secret
        IAcsClient client = new DefaultAcsClient(profile);
        String biz = "222";  //您在控制台上创建的、采用RPH5BioOnly认证方案的认证场景标识, 创建方法：https://help.aliyun.com/document_detail/59975.html
        String token = null; //认证token, 表达一次认证会话
        int statusCode = -1; //-1 未认证, 0 认证中, 1 认证通过, 2 认证不通过
        GetStatusRequest getStatusRequest = new GetStatusRequest();
        getStatusRequest.setBiz(biz);
        getStatusRequest.setTicketId(ticketId);
        try {
            GetStatusResponse response = client.getAcsResponse(getStatusRequest);
            statusCode = response.getData().getStatusCode();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        GetMaterialsRequest getMaterialsRequest = new GetMaterialsRequest();
        getMaterialsRequest.setBiz(biz);
        getMaterialsRequest.setTicketId(ticketId);
        IdcardValidate iv = new IdcardValidate();
        if(GlobalParams.REALNAME_STATE_SUCCESS == statusCode || GlobalParams.REALNAME_STATE_FALSE == statusCode) { //认证通过or认证不通过
            try {
                GetMaterialsResponse response = client.getAcsResponse(getMaterialsRequest);
                if(statusCode == GlobalParams.REALNAME_STATE_SUCCESS) {
                    iv.setIdentificationnumber(response.getData().getIdentificationNumber());
                    iv.setName(response.getData().getName());
                }
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        iv.setState(statusCode);
        iv.setTaskId(ticketId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("json", iv);
        return jsonObject;
    }
}
