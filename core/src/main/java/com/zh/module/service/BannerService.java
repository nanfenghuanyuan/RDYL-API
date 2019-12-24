package com.zh.module.service;

import com.zh.module.entity.Banner;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-23 19:46:57
 **/ 
public interface BannerService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:46:57
     **/ 
    int insert(Banner record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:46:57
     **/ 
    int insertSelective(Banner record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:46:57
     **/ 
    int updateByPrimaryKey(Banner record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:46:57
     **/ 
    int updateByPrimaryKeySelective(Banner record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:46:57
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:46:57
     **/ 
    Banner selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:46:57
     **/ 
    List<Banner> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:46:57
     **/ 
    List<Banner> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-23 19:46:57
     **/ 
    int selectCount(Map<Object, Object> param);
}