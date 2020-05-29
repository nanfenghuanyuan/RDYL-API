package com.zh.module.service;

import com.zh.module.entity.Flow;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-25 17:52:13
 **/ 
public interface FlowService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 17:52:13
     **/ 
    int insert(Flow record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 17:52:13
     **/ 
    int insertSelective(Flow record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 17:52:13
     **/ 
    int updateByPrimaryKey(Flow record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 17:52:13
     **/ 
    int updateByPrimaryKeySelective(Flow record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-25 17:52:13
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 17:52:13
     **/ 
    Flow selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 17:52:13
     **/ 
    List<Flow> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 17:52:13
     **/ 
    List<Flow> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 17:52:13
     **/ 
    int selectCount(Map<Object, Object> param);

    String selectPersonProfitSumAmount(Integer userId, String type);

    List<Map<String, Object>> selectByTransferList(Integer id, Integer pageInt, Integer rowsInt);
}