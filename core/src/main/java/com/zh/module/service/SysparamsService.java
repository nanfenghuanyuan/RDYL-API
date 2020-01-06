package com.zh.module.service;

import com.zh.module.entity.Sysparams;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-06 15:27:03
 **/ 
public interface SysparamsService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:27:03
     **/ 
    int insert(Sysparams record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:27:03
     **/ 
    int insertSelective(Sysparams record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:27:03
     **/ 
    int updateByPrimaryKey(Sysparams record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:27:03
     **/ 
    int updateByPrimaryKeySelective(Sysparams record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:27:03
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:27:03
     **/ 
    Sysparams selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:27:03
     **/ 
    List<Sysparams> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:27:03
     **/ 
    List<Sysparams> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:27:03
     **/ 
    int selectCount(Map<Object, Object> param);
    Sysparams getValByKey(String key);
    String getValStringByKey(String key);
}