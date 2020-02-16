package com.zh.module.service.impl;

import com.zh.module.dao.BlackListMapper;
import com.zh.module.entity.BlackList;
import com.zh.module.service.BlackListService;

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
 * @date: 2020-02-16 23:02:16
 **/ 
@Service("blackListService")
public class BlackListServiceImpl implements BlackListService {
    @Resource
    private BlackListMapper blackListMapper;

    private static final Logger logger = LoggerFactory.getLogger(BlackListServiceImpl.class);

    @Override
    public int insert(BlackList record) {
        return this.blackListMapper.insert(record);
    }

    @Override
    public int insertSelective(BlackList record) {
        return this.blackListMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(BlackList record) {
        return this.blackListMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BlackList record) {
        return this.blackListMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.blackListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public BlackList selectByPrimaryKey(Integer id) {
        return this.blackListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BlackList> selectAll(Map<Object, Object> param) {
        return this.blackListMapper.selectAll(param);
    }

    @Override
    public List<BlackList> selectPaging(Map<Object, Object> param) {
        return this.blackListMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.blackListMapper.selectCount(param);
    }

    @Override
    public BlackList selectByUserId(Integer userId) {
        Map<Object, Object> param = new HashMap<>();
        param.put("userId", userId);
        List<BlackList> blackLists = this.blackListMapper.selectAll(param);
        return blackLists == null || blackLists.size() == 0 ? null : blackLists.get(0);
    }
}