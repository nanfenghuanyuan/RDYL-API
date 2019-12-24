package com.zh.module.service.impl;

import com.zh.module.dao.PetsMatchingListMapper;
import com.zh.module.entity.PetsMatchingList;
import com.zh.module.service.PetsMatchingListService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-23 21:09:53
 **/ 
@Service("petsMatchingListService")
public class PetsMatchingListServiceImpl implements PetsMatchingListService {
    @Resource
    private PetsMatchingListMapper petsMatchingListMapper;

    private static final Logger logger = LoggerFactory.getLogger(PetsMatchingListServiceImpl.class);

    @Override
    public int insert(PetsMatchingList record) {
        return this.petsMatchingListMapper.insert(record);
    }

    @Override
    public int insertSelective(PetsMatchingList record) {
        return this.petsMatchingListMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(PetsMatchingList record) {
        return this.petsMatchingListMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(PetsMatchingList record) {
        return this.petsMatchingListMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.petsMatchingListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PetsMatchingList selectByPrimaryKey(Integer id) {
        return this.petsMatchingListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PetsMatchingList> selectAll(Map<Object, Object> param) {
        return this.petsMatchingListMapper.selectAll(param);
    }

    @Override
    public List<PetsMatchingList> selectPaging(Map<Object, Object> param) {
        return this.petsMatchingListMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.petsMatchingListMapper.selectCount(param);
    }

    @Override
    public List<PetsMatchingList> selectByHomePage(Map<Object, Object> map) {
        return this.petsMatchingListMapper.selectByHomePage(map);
    }
}