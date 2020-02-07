package com.zh.module.service;

import com.zh.module.entity.DailyCount;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-02-07 14:17:44
 **/ 
public interface DailyCountService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-02-07 14:17:44
     **/ 
    int insert(DailyCount record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-02-07 14:17:44
     **/ 
    int insertSelective(DailyCount record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-02-07 14:17:44
     **/ 
    int updateByPrimaryKey(DailyCount record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-02-07 14:17:44
     **/ 
    int updateByPrimaryKeySelective(DailyCount record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-02-07 14:17:44
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-02-07 14:17:44
     **/ 
    DailyCount selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-02-07 14:17:44
     **/ 
    List<DailyCount> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-02-07 14:17:44
     **/ 
    List<DailyCount> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-02-07 14:17:44
     **/ 
    int selectCount(Map<Object, Object> param);
}