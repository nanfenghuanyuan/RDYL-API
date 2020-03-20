package com.zh.module.service.impl;

import com.zh.module.dao.PetsCountMapper;
import com.zh.module.entity.PetsCount;
import com.zh.module.service.PetsCountService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2020-03-20 20:29:41
 **/ 
@Service("petsCountService")
public class PetsCountServiceImpl implements PetsCountService {
    @Resource
    private PetsCountMapper petsCountMapper;

    private static final Logger logger = LoggerFactory.getLogger(PetsCountServiceImpl.class);

    @Override
    public int insert(PetsCount record) {
        return this.petsCountMapper.insert(record);
    }

    @Override
    public int insertSelective(PetsCount record) {
        return this.petsCountMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(PetsCount record) {
        return this.petsCountMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(PetsCount record) {
        return this.petsCountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.petsCountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PetsCount selectByPrimaryKey(Integer id) {
        return this.petsCountMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PetsCount> selectAll(Map<Object, Object> param) {
        return this.petsCountMapper.selectAll(param);
    }

    @Override
    public List<PetsCount> selectPaging(Map<Object, Object> param) {
        return this.petsCountMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.petsCountMapper.selectCount(param);
    }
}