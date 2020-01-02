package com.zh.module.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.NoticeBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Notice;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-10-17 18:53
 */
@RestController
@RequestMapping(value = "notice")
public class NoticeController {

    @Autowired
    private NoticeBiz noticeBiz;

    /**
     * 公告、帮助列表
     * @param
     * @param  type 0弹窗 1公告 2帮助
     * @return
     */
    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String list(Integer rows, Integer page, Integer type){
        try {
            if(type == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if(page == null){
                page = 0;
            }

            PageModel pageModel = new PageModel(page, rows);
            return noticeBiz.list(type, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 公告、帮助列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value="",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String list(@PathVariable Integer id){
        try {

            return noticeBiz.get(id);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
