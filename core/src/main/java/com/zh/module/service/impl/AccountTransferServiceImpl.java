package com.zh.module.service.impl;

import com.zh.module.dao.AccountTransferMapper;
import com.zh.module.entity.AccountTransfer;
import com.zh.module.service.AccountTransferService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-13 20:59:11
 **/ 
@Service("accountTransferService")
public class AccountTransferServiceImpl implements AccountTransferService {
    @Resource
    private AccountTransferMapper accountTransferMapper;

    private static final Logger logger = LoggerFactory.getLogger(AccountTransferServiceImpl.class);

    @Override
    public int insert(AccountTransfer record) {
        return this.accountTransferMapper.insert(record);
    }

    @Override
    public int insertSelective(AccountTransfer record) {
        return this.accountTransferMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(AccountTransfer record) {
        return this.accountTransferMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AccountTransfer record) {
        return this.accountTransferMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.accountTransferMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AccountTransfer selectByPrimaryKey(Integer id) {
        return this.accountTransferMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AccountTransfer> selectAll(Map<Object, Object> param) {
        return this.accountTransferMapper.selectAll(param);
    }

    @Override
    public List<AccountTransfer> selectPaging(Map<Object, Object> param) {
        return this.accountTransferMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.accountTransferMapper.selectCount(param);
    }
}