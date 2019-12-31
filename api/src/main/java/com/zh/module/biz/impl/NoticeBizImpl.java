package com.zh.module.biz.impl;

import com.zh.module.biz.NoticeBiz;
import com.zh.module.dto.Result;
import com.zh.module.entity.Notice;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.NoticeModel;
import com.zh.module.model.PageModel;
import com.zh.module.service.*;
import com.zh.module.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
@Transactional
public class NoticeBizImpl implements NoticeBiz {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String list(Integer type, PageModel pageModel) {
        Map<Object, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("firstResult", pageModel.getFirstResult());
        map.put("maxResult", pageModel.getMaxResult());
        List<Notice> notices = noticeService.selectPaging(map);
        List<NoticeModel> list = new LinkedList<>();
        for(Notice notice : notices){
            NoticeModel noticeModel = new NoticeModel();
            noticeModel.setId(notice.getId());
            noticeModel.setTime(DateUtils.getDateFormate(notice.getCreateTime()));
            noticeModel.setTitle(notice.getTitle());
            list.add(noticeModel);
        }
        return Result.toResult(ResultCode.SUCCESS, list);
    }

    @Override
    public String get(Integer id) {
        Notice notice = noticeService.selectByPrimaryKey(id);
        return Result.toResult(ResultCode.SUCCESS, notice);
    }
}