package com.zh.module.service;

import com.zh.module.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-06 15:13:49
 **/ 
public interface AccountService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:13:49
     **/ 
    int insert(Account record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:13:49
     **/ 
    int insertSelective(Account record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:13:49
     **/ 
    int updateByPrimaryKey(Account record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:13:49
     **/ 
    int updateByPrimaryKeySelective(Account record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:13:49
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:13:49
     **/ 
    Account selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:13:49
     **/ 
    List<Account> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:13:49
     **/ 
    List<Account> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:13:49
     **/ 
    int selectCount(Map<Object, Object> param);

    Account selectByUserIdAndAccountTypeAndType(int accountTypeActive, int os, Integer id);

    void updateAccountAndInsertFlow(Integer userId, Integer accountType, Integer coinType,
                                    BigDecimal availIncrement, BigDecimal frozenIncrement, Integer operId, String operType, Integer relateId);
    String selectSumAmountByAccountTypeAndCoinType(Integer id, int accountType, int coinType);
}