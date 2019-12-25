package com.zh.module.service.impl;

import com.zh.module.dao.NoticeMapper;
import com.zh.module.entity.Notice;
import com.zh.module.service.NoticeService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-25 11:50:17
 **/ 
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    private static final Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Override
    public int insert(Notice record) {
        return this.noticeMapper.insert(record);
    }

    @Override
    public int insertSelective(Notice record) {
        return this.noticeMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Notice record) {
        return this.noticeMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Notice record) {
        return this.noticeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.noticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Notice selectByPrimaryKey(Integer id) {
        return this.noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Notice> selectAll(Map<Object, Object> param) {
        return this.noticeMapper.selectAll(param);
    }

    @Override
    public List<Notice> selectPaging(Map<Object, Object> param) {
        return this.noticeMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.noticeMapper.selectCount(param);
    }

    @Override
    public Notice seletByStart() {
        return this.noticeMapper.seletByStart();
    }
}