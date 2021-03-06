package com.zh.module.dao;

import com.zh.module.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AccountMapper {
    int insert(Account record);

    int insertSelective(Account record);

    int updateByPrimaryKey(Account record);

    int updateByPrimaryKeySelective(Account record);

    int deleteByPrimaryKey(Integer id);

    Account selectByPrimaryKey(Integer id);

    List<Account> selectAll(Map<Object, Object> param);

    List<Account> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    int updateBalance(Account account);

    String selectSumAmountByAccountTypeAndCoinType(@Param("userId") Integer id, @Param("accountType") int accountType, @Param("coinType") int coinType);

    String queryByUserIdAndAccountTypeAndType(@Param("id") Integer id);
}