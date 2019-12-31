package com.zh.module.service;

import com.zh.module.entity.CoinManager;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-31 16:40:21
 **/ 
public interface CoinManagerService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:40:21
     **/ 
    int insert(CoinManager record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:40:21
     **/ 
    int insertSelective(CoinManager record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:40:21
     **/ 
    int updateByPrimaryKey(CoinManager record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:40:21
     **/ 
    int updateByPrimaryKeySelective(CoinManager record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:40:21
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:40:21
     **/ 
    CoinManager selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:40:21
     **/ 
    List<CoinManager> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:40:21
     **/ 
    List<CoinManager> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-31 16:40:21
     **/ 
    int selectCount(Map<Object, Object> param);
}