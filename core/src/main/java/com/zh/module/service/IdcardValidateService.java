package com.zh.module.service;

import com.zh.module.entity.IdcardValidate;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-06 15:17:34
 **/ 
public interface IdcardValidateService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:17:34
     **/ 
    int insert(IdcardValidate record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:17:34
     **/ 
    int insertSelective(IdcardValidate record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:17:34
     **/ 
    int updateByPrimaryKey(IdcardValidate record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:17:34
     **/ 
    int updateByPrimaryKeySelective(IdcardValidate record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:17:34
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:17:34
     **/ 
    IdcardValidate selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:17:34
     **/ 
    List<IdcardValidate> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:17:34
     **/ 
    List<IdcardValidate> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:17:34
     **/ 
    int selectCount(Map<Object, Object> param);
    List<?> queryValidateTimes(Map<String, Object> map);
    IdcardValidate queryByTaskId(String taskId);
    List<Map<String, Object>> selectConditionPaging(Map<Object, Object> param);
    int selectConditionCount(Map<Object, Object> param);
}