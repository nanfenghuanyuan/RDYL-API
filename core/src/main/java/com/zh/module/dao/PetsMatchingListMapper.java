package com.zh.module.dao;

import com.zh.module.entity.PetsMatchingList;
import java.util.List;
import java.util.Map;

public interface PetsMatchingListMapper {
    int insert(PetsMatchingList record);

    int insertSelective(PetsMatchingList record);

    int updateByPrimaryKey(PetsMatchingList record);

    int updateByPrimaryKeySelective(PetsMatchingList record);

    int deleteByPrimaryKey(Integer id);

    PetsMatchingList selectByPrimaryKey(Integer id);

    List<PetsMatchingList> selectAll(Map<Object, Object> param);

    List<PetsMatchingList> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String, Object>> selectListPaging(Map<Object, Object> param);

    PetsMatchingList selectByPetListIdAndActive(Integer id);
}