package com.zh.module.service.impl;

import com.zh.module.dao.PetsListMapper;
import com.zh.module.entity.PetsList;
import com.zh.module.service.PetsListService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-30 13:57:37
 **/ 
@Service("petsListService")
public class PetsListServiceImpl implements PetsListService {
    @Resource
    private PetsListMapper petsListMapper;

    private static final Logger logger = LoggerFactory.getLogger(PetsListServiceImpl.class);

    @Override
    public int insert(PetsList record) {
        return this.petsListMapper.insert(record);
    }

    @Override
    public int insertSelective(PetsList record) {
        return this.petsListMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(PetsList record) {
        return this.petsListMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(PetsList record) {
        return this.petsListMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.petsListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PetsList selectByPrimaryKey(Integer id) {
        return this.petsListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PetsList> selectAll(Map<Object, Object> param) {
        return this.petsListMapper.selectAll(param);
    }

    @Override
    public List<PetsList> selectPaging(Map<Object, Object> param) {
        return this.petsListMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.petsListMapper.selectCount(param);
    }
    @Override
    public List<Map<String, Object>> selectListPaging(Map<Object, Object> param) {
        return this.petsListMapper.selectListPaging(param);
    }

    @Override
    public List<PetsList> selectDoProfit(Map<Object, Object> param) {
        return this.petsListMapper.selectDoProfit(param);
    }

    @Override
    public String selectSumAmountByUser(Integer id) {
        return this.petsListMapper.selectSumAmountByUser(id);
    }
}