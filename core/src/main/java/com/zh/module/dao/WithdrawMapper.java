package com.zh.module.dao;

import com.zh.module.entity.Withdraw;
import java.util.List;
import java.util.Map;

public interface WithdrawMapper {
    int insert(Withdraw record);

    int insertSelective(Withdraw record);

    int updateByPrimaryKey(Withdraw record);

    int updateByPrimaryKeySelective(Withdraw record);

    int deleteByPrimaryKey(Integer id);

    Withdraw selectByPrimaryKey(Integer id);

    List<Withdraw> selectAll(Map<Object, Object> param);

    List<Withdraw> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}