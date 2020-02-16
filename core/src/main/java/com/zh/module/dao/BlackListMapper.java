package com.zh.module.dao;

import com.zh.module.entity.BlackList;
import java.util.List;
import java.util.Map;

public interface BlackListMapper {
    int insert(BlackList record);

    int insertSelective(BlackList record);

    int updateByPrimaryKey(BlackList record);

    int updateByPrimaryKeySelective(BlackList record);

    int deleteByPrimaryKey(Integer id);

    BlackList selectByPrimaryKey(Integer id);

    List<BlackList> selectAll(Map<Object, Object> param);

    List<BlackList> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}