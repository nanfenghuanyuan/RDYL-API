package com.zh.module.dao;

import com.zh.module.entity.DailyCount;
import java.util.List;
import java.util.Map;

public interface DailyCountMapper {
    int insert(DailyCount record);

    int insertSelective(DailyCount record);

    int updateByPrimaryKey(DailyCount record);

    int updateByPrimaryKeySelective(DailyCount record);

    int deleteByPrimaryKey(Integer id);

    DailyCount selectByPrimaryKey(Integer id);

    List<DailyCount> selectAll(Map<Object, Object> param);

    List<DailyCount> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}