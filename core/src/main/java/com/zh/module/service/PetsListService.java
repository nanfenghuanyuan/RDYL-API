package com.zh.module.service;

import com.zh.module.entity.PetsList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-23 20:03:16
 **/ 
public interface PetsListService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-23 20:03:16
     **/ 
    int insert(PetsList record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-23 20:03:16
     **/ 
    int insertSelective(PetsList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-23 20:03:16
     **/ 
    int updateByPrimaryKey(PetsList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-23 20:03:16
     **/ 
    int updateByPrimaryKeySelective(PetsList record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-23 20:03:16
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 20:03:16
     **/ 
    PetsList selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 20:03:16
     **/ 
    List<PetsList> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 20:03:16
     **/ 
    List<PetsList> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 20:03:16
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 首页初始化
     * @param map
     * @return
     */
    List<PetsList> selectByHomePage(Map<Object, Object> map);
}