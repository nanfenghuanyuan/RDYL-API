package com.zh.module.controller;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zh.module.async.FileUtil;
import com.zh.module.biz.FileBiz;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.enums.ResultCode;
import com.zh.module.service.SysparamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileBiz fileBiz;
    @Autowired
    private SysparamsService sysparamsService;

    // 设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "heYulmBpbOCjg8drZWAdeCmG3Mb0vPFoDlC_4p8x";
    String SECRET_KEY = "Z97nlbHkmSwgLLv6yt1QfAi-px77meyagcPHsZ2b";
    // 要上传的空间
    String bucketname = "rdyl";

    // 密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 构造一个带指定Zone对象的配置类,不同的七云牛存储区域调用不同的zone
    Configuration cfg = new Configuration(Zone.zone0());
    // ...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);

    // 测试域名，只有30天有效期
    private static String QINIU_IMAGE_DOMAIN = "http://q7ftp0xay.bkt.clouddn.com/";

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    private String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    private String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            // 判断是否是合法的文件后缀
            if (!FileUtil.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            // 调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            return null;
        }
    }
    /**
     * 文件上传返回url
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public String uploadPicture(@RequestParam(value="file",required=false) MultipartFile file, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        File targetFile=null;
        //返回存储路径
        String url="";
        try {
            url = saveImage(file);
            map.put("imgPath", url);
            map.put("fileName", "");
            return Result.toResult(ResultCode.SUCCESS, map);
        } catch (Exception e) {
            log.info(e.getMessage());
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }


    /**
     *author:zhaohe
     * IO流读取图片
     * @param imgUrl 图片url
     */
    @RequestMapping(value = "/showImg",method = RequestMethod.GET)
    public void IoReadImage(String imgUrl, HttpServletResponse response) throws IOException {
        fileBiz.IoReadImage(imgUrl,response);
    }
}
