package com.zh.module.service;

import com.zh.module.entity.Pets;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-23 19:38:58
 **/ 
public interface PetsService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:38:58
     **/ 
    int insert(Pets record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:38:58
     **/ 
    int insertSelective(Pets record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:38:58
     **/ 
    int updateByPrimaryKey(Pets record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:38:58
     **/ 
    int updateByPrimaryKeySelective(Pets record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:38:58
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:38:58
     **/ 
    Pets selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:38:58
     **/ 
    List<Pets> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:38:58
     **/ 
    List<Pets> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:38:58
     **/ 
    int selectCount(Map<Object, Object> param);


    /**
     * 首页初始化
     * @return
     */
    List<Pets> homePageInitPets();

    /**
     * 根据等级查询
     * @param level
     * @return
     */
    Pets selectByLevel(Integer level);
}