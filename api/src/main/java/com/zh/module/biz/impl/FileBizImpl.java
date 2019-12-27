package com.zh.module.biz.impl;

import com.zh.module.biz.FileBiz;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-04-24 14:17
 */
@Service
public class FileBizImpl implements FileBiz {
    //注入配置文件application.yml中设置的图片存放子目录名
    private String GOODS_IMG_PATH = "C:/img";
    /**
     *author：weijiakun
     * IO流读取图片
     * @param imgUrl 图片url，即图片保存在服务器上的名称
     */
    @Override
    public void IoReadImage(String imgUrl, HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;
        String upload = null;

        try {
            //获取图片存放路径
            String imgPath = GOODS_IMG_PATH + "/" + imgUrl;
            ips = new FileInputStream(new File(imgPath));
            String type = imgUrl.substring(imgUrl.indexOf(".")+1);
            if (type.equals("png")){
                response.setContentType("image/png");
            }
            if (type.equals("jpeg")){
                response.setContentType("image/jpeg");
            }
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            ips.close();
        }
    }
}
