package com.zh.module.service.impl;

import com.zh.module.dao.AppVersionMapper;
import com.zh.module.entity.AppVersion;
import com.zh.module.service.AppVersionService;

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
 * @date: 2020-01-31 16:59:23
 **/ 
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;

    private static final Logger logger = LoggerFactory.getLogger(AppVersionServiceImpl.class);

    @Override
    public int insert(AppVersion record) {
        return this.appVersionMapper.insert(record);
    }

    @Override
    public int insertSelective(AppVersion record) {
        return this.appVersionMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(AppVersion record) {
        return this.appVersionMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AppVersion record) {
        return this.appVersionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.appVersionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AppVersion selectByPrimaryKey(Integer id) {
        return this.appVersionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AppVersion> selectAll(Map<Object, Object> param) {
        return this.appVersionMapper.selectAll(param);
    }

    @Override
    public List<AppVersion> selectPaging(Map<Object, Object> param) {
        return this.appVersionMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.appVersionMapper.selectCount(param);
    }

    @Override
    public List<AppVersion> getByVersion(Integer version) {
        Map<Object, Object> map = new HashMap();
        map.put("version", version);
        List<AppVersion> users = appVersionMapper.getByVersionAndType(map);
        return users==null||users.isEmpty()?null:users;
    }
}