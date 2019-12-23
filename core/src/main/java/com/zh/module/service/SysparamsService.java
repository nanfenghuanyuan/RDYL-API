package com.zh.module.service;

import com.zh.module.entity.Sysparams;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-20 16:04:55
 **/ 
public interface SysparamsService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:04:55
     **/ 
    int insert(Sysparams record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:04:55
     **/ 
    int insertSelective(Sysparams record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:04:55
     **/ 
    int updateByPrimaryKey(Sysparams record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:04:55
     **/ 
    int updateByPrimaryKeySelective(Sysparams record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:04:55
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:04:55
     **/ 
    Sysparams selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:04:55
     **/ 
    List<Sysparams> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:04:55
     **/ 
    List<Sysparams> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:04:55
     **/ 
    int selectCount(Map<Object, Object> param);

    Sysparams getValByKey(String key);
}