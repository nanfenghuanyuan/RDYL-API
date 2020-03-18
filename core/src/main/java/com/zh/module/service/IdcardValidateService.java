package com.zh.module.service;

import com.zh.module.entity.IdcardValidate;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-08 18:00:18
 **/ 
public interface IdcardValidateService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-08 18:00:18
     **/ 
    int insert(IdcardValidate record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-08 18:00:18
     **/ 
    int insertSelective(IdcardValidate record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-08 18:00:18
     **/ 
    int updateByPrimaryKey(IdcardValidate record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-08 18:00:18
     **/ 
    int updateByPrimaryKeySelective(IdcardValidate record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-08 18:00:18
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-08 18:00:18
     **/ 
    IdcardValidate selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-08 18:00:18
     **/ 
    List<IdcardValidate> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-08 18:00:18
     **/ 
    List<IdcardValidate> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-08 18:00:18
     **/ 
    int selectCount(Map<Object, Object> param);
    int selectConditionCount(Map<Object, Object> param);
    List<Map<String, Object>> selectConditionPaging(Map<Object, Object> param);
    IdcardValidate queryByTaskId(String taskId);
    List<?> queryValidateTimes(Map<String, Object> map);

    int selectCountByTime(String start, String end);
}