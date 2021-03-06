package com.zh.module.dao;

import com.zh.module.entity.Recharge;
import java.util.List;
import java.util.Map;

public interface RechargeMapper {
    int insert(Recharge record);

    int insertSelective(Recharge record);

    int updateByPrimaryKey(Recharge record);

    int updateByPrimaryKeySelective(Recharge record);

    int deleteByPrimaryKey(Integer id);

    Recharge selectByPrimaryKey(Integer id);

    List<Recharge> selectAll(Map<Object, Object> param);

    List<Recharge> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}