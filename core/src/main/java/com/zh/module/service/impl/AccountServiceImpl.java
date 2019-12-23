package com.zh.module.service.impl;

import com.zh.module.dao.AccountMapper;
import com.zh.module.entity.Account;
import com.zh.module.service.AccountService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-20 18:18:17
 **/ 
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public int insert(Account record) {
        return this.accountMapper.insert(record);
    }

    @Override
    public int insertSelective(Account record) {
        return this.accountMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Account record) {
        return this.accountMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Account record) {
        return this.accountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.accountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Account selectByPrimaryKey(Integer id) {
        return this.accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Account> selectAll(Map<Object, Object> param) {
        return this.accountMapper.selectAll(param);
    }

    @Override
    public List<Account> selectPaging(Map<Object, Object> param) {
        return this.accountMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.accountMapper.selectCount(param);
    }
}