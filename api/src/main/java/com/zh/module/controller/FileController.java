package com.zh.module.controller;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Controller
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileBiz fileBiz;
    @Autowired
    private SysparamsService sysparamsService;
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
        //获取文件名加后缀
        String fileName=file.getOriginalFilename();
        log.info("文件名：" + fileName);
        if(fileName!=null&&fileName!=""){
            //文件存储位置
//            String path = "/home/installPackage/imgs/";
            String path = "C:/img";
            System.out.println(path);
            //文件后缀
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            //新的文件名
            fileName=System.currentTimeMillis()+"_"+new Random().nextInt(1000)+fileF;
            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd);
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                String sysUrl = sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL);
                url= sysUrl + "/file/showImg.action?imgUrl="+fileAdd+"/"+fileName;
                map.put("imgPath", url);
                map.put("fileName", fileName);
                return Result.toResult(ResultCode.SUCCESS, map);
            } catch (Exception e) {
                log.info(e.getMessage());
                return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
            }
        }
        return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
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
