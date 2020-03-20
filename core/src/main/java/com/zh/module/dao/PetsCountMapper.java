package com.zh.module.dao;

import com.zh.module.entity.PetsCount;
import java.util.List;
import java.util.Map;

public interface PetsCountMapper {
    int insert(PetsCount record);

    int insertSelective(PetsCount record);

    int updateByPrimaryKey(PetsCount record);

    int updateByPrimaryKeySelective(PetsCount record);

    int deleteByPrimaryKey(Integer id);

    PetsCount selectByPrimaryKey(Integer id);

    List<PetsCount> selectAll(Map<Object, Object> param);

    List<PetsCount> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}