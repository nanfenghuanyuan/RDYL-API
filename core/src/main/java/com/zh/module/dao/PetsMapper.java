package com.zh.module.dao;

import com.zh.module.entity.Pets;
import java.util.List;
import java.util.Map;

public interface PetsMapper {
    int insert(Pets record);

    int insertSelective(Pets record);

    int updateByPrimaryKey(Pets record);

    int updateByPrimaryKeySelective(Pets record);

    int deleteByPrimaryKey(Integer id);

    Pets selectByPrimaryKey(Integer id);

    List<Pets> selectAll(Map<Object, Object> param);

    List<Pets> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Pets> homePageInitPets();
}