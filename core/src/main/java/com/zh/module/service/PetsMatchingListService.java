package com.zh.module.service;

import com.zh.module.entity.PetsMatchingList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-25 18:02:24
 **/ 
public interface PetsMatchingListService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 18:02:24
     **/ 
    int insert(PetsMatchingList record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 18:02:24
     **/ 
    int insertSelective(PetsMatchingList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 18:02:24
     **/ 
    int updateByPrimaryKey(PetsMatchingList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 18:02:24
     **/ 
    int updateByPrimaryKeySelective(PetsMatchingList record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-25 18:02:24
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 18:02:24
     **/ 
    PetsMatchingList selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 18:02:24
     **/ 
    List<PetsMatchingList> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 18:02:24
     **/ 
    List<PetsMatchingList> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 18:02:24
     **/ 
    int selectCount(Map<Object, Object> param);
}