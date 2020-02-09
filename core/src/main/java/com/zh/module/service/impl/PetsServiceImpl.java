package com.zh.module.service.impl;

import com.zh.module.dao.PetsMapper;
import com.zh.module.entity.Pets;
import com.zh.module.service.PetsService;

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
 * @date: 2019-12-23 19:38:58
 **/ 
@Service("petsService")
public class PetsServiceImpl implements PetsService {
    @Resource
    private PetsMapper petsMapper;

    private static final Logger logger = LoggerFactory.getLogger(PetsServiceImpl.class);

    @Override
    public int insert(Pets record) {
        return this.petsMapper.insert(record);
    }

    @Override
    public int insertSelective(Pets record) {
        return this.petsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Pets record) {
        return this.petsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Pets record) {
        return this.petsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.petsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Pets selectByPrimaryKey(Integer id) {
        return this.petsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Pets> selectAll(Map<Object, Object> param) {
        return this.petsMapper.selectAll(param);
    }

    @Override
    public List<Pets> selectPaging(Map<Object, Object> param) {
        return this.petsMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.petsMapper.selectCount(param);
    }

    @Override
    public List<Pets> homePageInitPets() {
        return petsMapper.homePageInitPets();
    }

    @Override
    public Pets selectByLevel(Integer level) {
        Map<Object, Object> param = new HashMap<>();
        param.put("level", level);
        List<Pets> petsList = this.petsMapper.selectAll(param);
        return petsList.size() == 0 ? null : petsList.get(0);
    }

    @Override
    public Pets selectByPrice(String amount) {
        return this.petsMapper.selectByPrice(amount);
    }
}