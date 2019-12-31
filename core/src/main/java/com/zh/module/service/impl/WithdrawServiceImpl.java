package com.zh.module.service.impl;

import com.zh.module.dao.WithdrawMapper;
import com.zh.module.entity.Withdraw;
import com.zh.module.service.WithdrawService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-31 15:47:24
 **/ 
@Service("withdrawService")
public class WithdrawServiceImpl implements WithdrawService {
    @Resource
    private WithdrawMapper withdrawMapper;

    private static final Logger logger = LoggerFactory.getLogger(WithdrawServiceImpl.class);

    @Override
    public int insert(Withdraw record) {
        return this.withdrawMapper.insert(record);
    }

    @Override
    public int insertSelective(Withdraw record) {
        return this.withdrawMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Withdraw record) {
        return this.withdrawMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Withdraw record) {
        return this.withdrawMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.withdrawMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Withdraw selectByPrimaryKey(Integer id) {
        return this.withdrawMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Withdraw> selectAll(Map<Object, Object> param) {
        return this.withdrawMapper.selectAll(param);
    }

    @Override
    public List<Withdraw> selectPaging(Map<Object, Object> param) {
        return this.withdrawMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.withdrawMapper.selectCount(param);
    }
}