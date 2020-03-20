package com.zh.module.service;

import com.zh.module.entity.PetsCount;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-03-20 20:29:41
 **/ 
public interface PetsCountService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-03-20 20:29:41
     **/ 
    int insert(PetsCount record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-03-20 20:29:41
     **/ 
    int insertSelective(PetsCount record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-03-20 20:29:41
     **/ 
    int updateByPrimaryKey(PetsCount record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-03-20 20:29:41
     **/ 
    int updateByPrimaryKeySelective(PetsCount record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-03-20 20:29:41
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-03-20 20:29:41
     **/ 
    PetsCount selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-03-20 20:29:41
     **/ 
    List<PetsCount> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-03-20 20:29:41
     **/ 
    List<PetsCount> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-03-20 20:29:41
     **/ 
    int selectCount(Map<Object, Object> param);
}