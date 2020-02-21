package com.zh.module.dao;

import com.zh.module.entity.WithdrawQuote;
import java.util.List;
import java.util.Map;

public interface WithdrawQuoteMapper {
    int insert(WithdrawQuote record);

    int insertSelective(WithdrawQuote record);

    int updateByPrimaryKey(WithdrawQuote record);

    int updateByPrimaryKeySelective(WithdrawQuote record);

    int deleteByPrimaryKey(Integer id);

    WithdrawQuote selectByPrimaryKey(Integer id);

    List<WithdrawQuote> selectAll(Map<Object, Object> param);

    List<WithdrawQuote> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}