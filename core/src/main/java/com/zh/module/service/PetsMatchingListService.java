package com.zh.module.service;

import com.zh.module.entity.PetsMatchingList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-02 17:45:53
 **/ 
public interface PetsMatchingListService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-02 17:45:53
     **/ 
    int insert(PetsMatchingList record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-02 17:45:53
     **/ 
    int insertSelective(PetsMatchingList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-02 17:45:53
     **/ 
    int updateByPrimaryKey(PetsMatchingList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-02 17:45:53
     **/ 
    int updateByPrimaryKeySelective(PetsMatchingList record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-02 17:45:53
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-02 17:45:53
     **/ 
    PetsMatchingList selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-02 17:45:53
     **/ 
    List<PetsMatchingList> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-02 17:45:53
     **/ 
    List<PetsMatchingList> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-02 17:45:53
     **/ 
    int selectCount(Map<Object, Object> param);

    List<Map<String, Object>> selectListPaging(Map<Object, Object> param);
    PetsMatchingList selectByPetListIdAndActive(Integer id);
}