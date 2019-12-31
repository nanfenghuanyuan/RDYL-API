package com.zh.module.service.impl;

import com.zh.module.dao.AccountMapper;
import com.zh.module.dao.FlowMapper;
import com.zh.module.entity.Account;
import com.zh.module.entity.Flow;
import com.zh.module.exception.BanlanceNotEnoughException;
import com.zh.module.service.AccountService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-20 18:18:17
 **/
@Slf4j
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private FlowMapper flowMapper;

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

    @Override
    public void updateAccountAndInsertFlow(Integer userId, Integer accountType, Integer coinType,
                                           BigDecimal availIncrement, BigDecimal frozenIncrement, Integer operId, String operType, Integer relateId) throws BanlanceNotEnoughException {

        Map<Object, Object> map = new HashMap();
        map.put("userid", userId);
        map.put("accounttype", accountType);
        map.put("cointype", coinType);
        List<Account> accountList = selectAll(map);

        Account account = new Account();
        account.setUserId(userId);
        account.setCoinType(coinType);
        account.setAccountType(accountType);
        account.setAvailbalance(availIncrement);
        account.setFrozenblance(frozenIncrement);

        int ut;
        if (accountList == null || accountList.isEmpty()) {
            if (availIncrement.compareTo(BigDecimal.ZERO) == -1 || frozenIncrement.compareTo(BigDecimal.ZERO) == -1) {
                log.info("账户余额不足，update account-->{}", account.toString());
                throw new BanlanceNotEnoughException("账户余额不足");
            }
            ut = insertSelective(account);
        } else {
            ut = this.accountMapper.updateBalance(account);
        }

        if (ut != 1) {
            log.info("账户余额不足，update account-->{}", account.toString());
            throw new BanlanceNotEnoughException("账户余额不足");
        }

        if (availIncrement.compareTo(BigDecimal.ZERO) != 0) {
            Flow flow = new Flow();
            flow.setUserId(userId);
            flow.setAccountType(accountType);
            flow.setCoinType(coinType);
            flow.setOperId(operId);
            flow.setOperType(operType);
            flow.setRelateId(relateId);
            flow.setAmount(availIncrement);
            flow.setResultAmount(account.getAvailbalance().add(availIncrement).toPlainString());
            flowMapper.insertSelective(flow);
        }else
        if (frozenIncrement.compareTo(BigDecimal.ZERO) != 0) {
            Flow flow = new Flow();
            flow.setUserId(userId);
            flow.setAccountType(accountType);
            flow.setCoinType(coinType);
            flow.setOperId(operId);
            flow.setOperType(operType);
            flow.setRelateId(relateId);
            flow.setAmount(frozenIncrement);
            flow.setResultAmount(account.getFrozenblance().add(frozenIncrement).toPlainString());
            flowMapper.insertSelective(flow);
        }
    }

    @Override
    public Account selectByUserIdAndAccountTypeAndType(int accountType, int coinType, Integer userId) {
        Map<Object, Object> param = new HashMap<>();
        param.put("accountType", accountType);
        param.put("coinType", coinType);
        param.put("userId", userId);
        List<Account> list = this.accountMapper.selectAll(param);
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public String selectSumAmountByAccountTypeAndCoinType(Integer id, int accountType, int coinType) {
        return this.accountMapper.selectSumAmountByAccountTypeAndCoinType(id, accountType, coinType);
    }
}