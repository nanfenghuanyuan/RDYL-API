package com.zh.module.dao;

import com.zh.module.entity.Withdraw;
import org.apache.ibatis.annotations.Param;

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

    String totalDayAmount(@Param("userId") Integer userId, @Param("coinType") Integer coinType, @Param("time") String today);

    int selectCounts(Map<Object, Object> param);
}