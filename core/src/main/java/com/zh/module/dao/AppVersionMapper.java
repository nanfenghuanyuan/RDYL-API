package com.zh.module.dao;

import com.zh.module.entity.AppVersion;
import java.util.List;
import java.util.Map;

public interface AppVersionMapper {
    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);

    int updateByPrimaryKeySelective(AppVersion record);

    int deleteByPrimaryKey(Integer id);

    AppVersion selectByPrimaryKey(Integer id);

    List<AppVersion> selectAll(Map<Object, Object> param);

    List<AppVersion> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<AppVersion> getByVersionAndType(Map<Object, Object> map);
}