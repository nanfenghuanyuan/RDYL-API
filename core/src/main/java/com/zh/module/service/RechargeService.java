package com.zh.module.service;

import com.zh.module.entity.Recharge;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-31 16:47:28
 **/ 
public interface RechargeService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:47:28
     **/ 
    int insert(Recharge record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:47:28
     **/ 
    int insertSelective(Recharge record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:47:28
     **/ 
    int updateByPrimaryKey(Recharge record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:47:28
     **/ 
    int updateByPrimaryKeySelective(Recharge record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:47:28
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:47:28
     **/ 
    Recharge selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:47:28
     **/ 
    List<Recharge> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:47:28
     **/ 
    List<Recharge> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:47:28
     **/ 
    int selectCount(Map<Object, Object> param);
}