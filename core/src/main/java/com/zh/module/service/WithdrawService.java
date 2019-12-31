package com.zh.module.service;

import com.zh.module.entity.Withdraw;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-31 15:47:24
 **/ 
public interface WithdrawService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-31 15:47:24
     **/ 
    int insert(Withdraw record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-31 15:47:24
     **/ 
    int insertSelective(Withdraw record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-31 15:47:24
     **/ 
    int updateByPrimaryKey(Withdraw record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-31 15:47:24
     **/ 
    int updateByPrimaryKeySelective(Withdraw record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-31 15:47:24
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 15:47:24
     **/ 
    Withdraw selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 15:47:24
     **/ 
    List<Withdraw> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 15:47:24
     **/ 
    List<Withdraw> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 15:47:24
     **/ 
    int selectCount(Map<Object, Object> param);
}