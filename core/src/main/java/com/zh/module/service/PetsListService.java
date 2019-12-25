package com.zh.module.service;

import com.zh.module.entity.PetsList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-25 10:06:29
 **/ 
public interface PetsListService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 10:06:29
     **/ 
    int insert(PetsList record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 10:06:29
     **/ 
    int insertSelective(PetsList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 10:06:29
     **/ 
    int updateByPrimaryKey(PetsList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 10:06:29
     **/ 
    int updateByPrimaryKeySelective(PetsList record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-25 10:06:29
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 10:06:29
     **/ 
    PetsList selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 10:06:29
     **/ 
    List<PetsList> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 10:06:29
     **/ 
    List<PetsList> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 10:06:29
     **/ 
    int selectCount(Map<Object, Object> param);
}