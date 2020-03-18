package com.zh.module.dao;

import com.zh.module.entity.ProfitRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProfitRecordMapper {
    int insert(ProfitRecord record);

    int insertSelective(ProfitRecord record);

    int updateByPrimaryKey(ProfitRecord record);

    int updateByPrimaryKeySelective(ProfitRecord record);

    int deleteByPrimaryKey(Integer id);

    ProfitRecord selectByPrimaryKey(Integer id);

    List<ProfitRecord> selectAll(Map<Object, Object> param);

    List<ProfitRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    String selectSumAmount(@Param("userId") Integer userId, @Param("type") int type);

    String selectCountByTime(@Param("start") String start, @Param("end") String end);
}