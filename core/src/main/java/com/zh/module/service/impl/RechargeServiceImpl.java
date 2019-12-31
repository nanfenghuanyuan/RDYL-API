package com.zh.module.service.impl;

import com.zh.module.dao.RechargeMapper;
import com.zh.module.entity.Recharge;
import com.zh.module.service.RechargeService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-31 16:47:28
 **/ 
@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;

    private static final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

    @Override
    public int insert(Recharge record) {
        return this.rechargeMapper.insert(record);
    }

    @Override
    public int insertSelective(Recharge record) {
        return this.rechargeMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Recharge record) {
        return this.rechargeMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Recharge record) {
        return this.rechargeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.rechargeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Recharge selectByPrimaryKey(Integer id) {
        return this.rechargeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Recharge> selectAll(Map<Object, Object> param) {
        return this.rechargeMapper.selectAll(param);
    }

    @Override
    public List<Recharge> selectPaging(Map<Object, Object> param) {
        return this.rechargeMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.rechargeMapper.selectCount(param);
    }
}