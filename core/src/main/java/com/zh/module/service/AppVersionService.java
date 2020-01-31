package com.zh.module.service;

import com.zh.module.entity.AppVersion;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-31 16:59:23
 **/ 
public interface AppVersionService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-31 16:59:23
     **/ 
    int insert(AppVersion record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-31 16:59:23
     **/ 
    int insertSelective(AppVersion record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-31 16:59:23
     **/ 
    int updateByPrimaryKey(AppVersion record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-31 16:59:23
     **/ 
    int updateByPrimaryKeySelective(AppVersion record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-31 16:59:23
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-31 16:59:23
     **/ 
    AppVersion selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-31 16:59:23
     **/ 
    List<AppVersion> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-31 16:59:23
     **/ 
    List<AppVersion> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-31 16:59:23
     **/ 
    int selectCount(Map<Object, Object> param);

    List<AppVersion> getByVersion(Integer version);
}