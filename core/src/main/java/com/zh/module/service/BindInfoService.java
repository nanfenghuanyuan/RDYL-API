package com.zh.module.service;

import com.zh.module.entity.BindInfo;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-25 20:55:41
 **/ 
public interface BindInfoService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 20:55:41
     **/ 
    int insert(BindInfo record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 20:55:41
     **/ 
    int insertSelective(BindInfo record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 20:55:41
     **/ 
    int updateByPrimaryKey(BindInfo record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 20:55:41
     **/ 
    int updateByPrimaryKeySelective(BindInfo record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-25 20:55:41
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 20:55:41
     **/ 
    BindInfo selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 20:55:41
     **/ 
    List<BindInfo> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 20:55:41
     **/ 
    List<BindInfo> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 20:55:41
     **/ 
    int selectCount(Map<Object, Object> param);

    List<BindInfo> queryByUser(Integer id);
}