package com.zh.module.service;

import com.zh.module.entity.BlackList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-02-16 23:02:16
 **/ 
public interface BlackListService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-02-16 23:02:16
     **/ 
    int insert(BlackList record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-02-16 23:02:16
     **/ 
    int insertSelective(BlackList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-02-16 23:02:16
     **/ 
    int updateByPrimaryKey(BlackList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-02-16 23:02:16
     **/ 
    int updateByPrimaryKeySelective(BlackList record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-02-16 23:02:16
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-02-16 23:02:16
     **/ 
    BlackList selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-02-16 23:02:16
     **/ 
    List<BlackList> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-02-16 23:02:16
     **/ 
    List<BlackList> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-02-16 23:02:16
     **/ 
    int selectCount(Map<Object, Object> param);

    BlackList selectByUserId(Integer userId);
}