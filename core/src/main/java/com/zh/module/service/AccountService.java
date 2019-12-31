package com.zh.module.service;

import com.zh.module.entity.Account;
import com.zh.module.exception.BanlanceNotEnoughException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-20 18:18:17
 **/ 
public interface AccountService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int insert(Account record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int insertSelective(Account record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int updateByPrimaryKey(Account record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int updateByPrimaryKeySelective(Account record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    Account selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    List<Account> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    List<Account> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 更新账户并保存流水
     * @param userId 用户id
     * @param accountType 账户类型
     * @param coinType 币种类型
     * @param availIncrement 可用余额增量
     * @param frozenIncrement 冻结余额增量
     * @param operId 操作人id
     * @param operType 操作类型
     * @param relateId 关联表id
     */
    void updateAccountAndInsertFlow(Integer userId, Integer accountType, Integer coinType,
                                    BigDecimal availIncrement, BigDecimal frozenIncrement, Integer operId, String operType, Integer relateId) throws BanlanceNotEnoughException;

    Account selectByUserIdAndAccountTypeAndType(int accountType, int coinType, Integer userId);

    String selectSumAmountByAccountTypeAndCoinType(Integer id, int accountType, int coinType);
}