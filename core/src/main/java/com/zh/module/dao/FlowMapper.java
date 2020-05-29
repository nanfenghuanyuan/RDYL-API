package com.zh.module.dao;

import com.zh.module.entity.Flow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FlowMapper {
    int insert(Flow record);

    int insertSelective(Flow record);

    int updateByPrimaryKey(Flow record);

    int updateByPrimaryKeySelective(Flow record);

    int deleteByPrimaryKey(Integer id);

    Flow selectByPrimaryKey(Integer id);

    List<Flow> selectAll(Map<Object, Object> param);

    List<Flow> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    String selectPersonProfitSumAmount(@Param("userId") Integer userId, @Param("operType") String operType);

    List<Map<String, Object>> selectByTransferList(Map<Object, Object> map);
}