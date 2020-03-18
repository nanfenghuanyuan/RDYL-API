package com.zh.module.service.impl;

import com.zh.module.dao.DailyCountMapper;
import com.zh.module.entity.DailyCount;
import com.zh.module.service.DailyCountService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2020-03-18 20:44:11
 **/ 
@Service("dailyCountService")
public class DailyCountServiceImpl implements DailyCountService {
    @Resource
    private DailyCountMapper dailyCountMapper;

    private static final Logger logger = LoggerFactory.getLogger(DailyCountServiceImpl.class);

    @Override
    public int insert(DailyCount record) {
        return this.dailyCountMapper.insert(record);
    }

    @Override
    public int insertSelective(DailyCount record) {
        return this.dailyCountMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(DailyCount record) {
        return this.dailyCountMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(DailyCount record) {
        return this.dailyCountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.dailyCountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DailyCount selectByPrimaryKey(Integer id) {
        return this.dailyCountMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DailyCount> selectAll(Map<Object, Object> param) {
        return this.dailyCountMapper.selectAll(param);
    }

    @Override
    public List<DailyCount> selectPaging(Map<Object, Object> param) {
        return this.dailyCountMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.dailyCountMapper.selectCount(param);
    }
}