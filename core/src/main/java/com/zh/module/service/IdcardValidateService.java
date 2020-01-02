package com.zh.module.service;

import com.zh.module.entity.IdcardValidate;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-02 11:05:33
 **/ 
public interface IdcardValidateService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-02 11:05:33
     **/ 
    int insert(IdcardValidate record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-02 11:05:33
     **/ 
    int insertSelective(IdcardValidate record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-02 11:05:33
     **/ 
    int updateByPrimaryKey(IdcardValidate record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-02 11:05:33
     **/ 
    int updateByPrimaryKeySelective(IdcardValidate record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-02 11:05:33
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-02 11:05:33
     **/ 
    IdcardValidate selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-02 11:05:33
     **/ 
    List<IdcardValidate> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-02 11:05:33
     **/ 
    List<IdcardValidate> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-02 11:05:33
     **/ 
    int selectCount(Map<Object, Object> param);
    /**
     * 实名认证校验次数
     * @return Map<String,Object>
     */
    List<?> queryValidateTimes(Map<String, Object> map);

    /**
     * 根据taskId查询
     * @param taskId
     * @return
     */
    IdcardValidate queryByTaskId(String taskId);

    /**
     * 后台条件查询实名认证记录
     * @param param
     * @return
     */
    List<Map<String,Object>> selectConditionPaging(Map<Object, Object> param);

    /**
     * 后台统计条件查询的统计数目
     * @param param
     * @return
     */
    int selectConditionCount(Map<Object, Object> param);

}