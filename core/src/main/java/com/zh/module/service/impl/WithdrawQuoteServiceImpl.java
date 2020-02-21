package com.zh.module.service.impl;

import com.zh.module.dao.WithdrawQuoteMapper;
import com.zh.module.entity.WithdrawQuote;
import com.zh.module.service.WithdrawQuoteService;

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
 * @date: 2020-02-21 14:01:13
 **/ 
@Service("withdrawQuoteService")
public class WithdrawQuoteServiceImpl implements WithdrawQuoteService {
    @Resource
    private WithdrawQuoteMapper withdrawQuoteMapper;

    private static final Logger logger = LoggerFactory.getLogger(WithdrawQuoteServiceImpl.class);

    @Override
    public int insert(WithdrawQuote record) {
        return this.withdrawQuoteMapper.insert(record);
    }

    @Override
    public int insertSelective(WithdrawQuote record) {
        return this.withdrawQuoteMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(WithdrawQuote record) {
        return this.withdrawQuoteMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(WithdrawQuote record) {
        return this.withdrawQuoteMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.withdrawQuoteMapper.deleteByPrimaryKey(id);
    }

    @Override
    public WithdrawQuote selectByPrimaryKey(Integer id) {
        return this.withdrawQuoteMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<WithdrawQuote> selectAll(Map<Object, Object> param) {
        return this.withdrawQuoteMapper.selectAll(param);
    }

    @Override
    public List<WithdrawQuote> selectPaging(Map<Object, Object> param) {
        return this.withdrawQuoteMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.withdrawQuoteMapper.selectCount(param);
    }

    @Override
    public WithdrawQuote selectByUser(Integer userId) {
        Map<Object, Object> param = new HashMap<>();
        param.put("userId", userId);
        List<WithdrawQuote> list = this.withdrawQuoteMapper.selectAll(param);
        return list == null || list.size() == 0 ? null : list.get(0);
    }
}