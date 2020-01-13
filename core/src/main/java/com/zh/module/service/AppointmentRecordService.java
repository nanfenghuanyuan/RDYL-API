package com.zh.module.service;

import com.zh.module.entity.AppointmentRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-13 11:31:17
 **/ 
public interface AppointmentRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-13 11:31:17
     **/ 
    int insert(AppointmentRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-13 11:31:17
     **/ 
    int insertSelective(AppointmentRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-13 11:31:17
     **/ 
    int updateByPrimaryKey(AppointmentRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-13 11:31:17
     **/ 
    int updateByPrimaryKeySelective(AppointmentRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-13 11:31:17
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-13 11:31:17
     **/ 
    AppointmentRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-13 11:31:17
     **/ 
    List<AppointmentRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-13 11:31:17
     **/ 
    List<AppointmentRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-13 11:31:17
     **/ 
    int selectCount(Map<Object, Object> param);
}