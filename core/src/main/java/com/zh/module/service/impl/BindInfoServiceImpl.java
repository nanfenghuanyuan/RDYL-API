package com.zh.module.service.impl;

import com.zh.module.dao.BindInfoMapper;
import com.zh.module.entity.BindInfo;
import com.zh.module.service.BindInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-25 20:55:41
 **/ 
@Service("bindInfoService")
public class BindInfoServiceImpl implements BindInfoService {
    @Resource
    private BindInfoMapper bindInfoMapper;

    private static final Logger logger = LoggerFactory.getLogger(BindInfoServiceImpl.class);

    @Override
    public int insert(BindInfo record) {
        return this.bindInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(BindInfo record) {
        return this.bindInfoMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(BindInfo record) {
        return this.bindInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BindInfo record) {
        return this.bindInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.bindInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public BindInfo selectByPrimaryKey(Integer id) {
        return this.bindInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BindInfo> selectAll(Map<Object, Object> param) {
        return this.bindInfoMapper.selectAll(param);
    }

    @Override
    public List<BindInfo> selectPaging(Map<Object, Object> param) {
        return this.bindInfoMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.bindInfoMapper.selectCount(param);
    }

    @Override
    public List<BindInfo> queryByUser(Integer id) {
        Map<Object,Object> map = new HashMap<>();
        map.put("userId",id);
        map.put("state",1);
        return bindInfoMapper.selectAll(map);
    }
}