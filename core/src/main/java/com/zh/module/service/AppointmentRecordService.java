package com.zh.module.service;

import com.zh.module.entity.AppointmentRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-30 19:30:44
 **/ 
public interface AppointmentRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-30 19:30:44
     **/ 
    int insert(AppointmentRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-30 19:30:44
     **/ 
    int insertSelective(AppointmentRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-30 19:30:44
     **/ 
    int updateByPrimaryKey(AppointmentRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-30 19:30:44
     **/ 
    int updateByPrimaryKeySelective(AppointmentRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-30 19:30:44
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-30 19:30:44
     **/ 
    AppointmentRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-30 19:30:44
     **/ 
    List<AppointmentRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-30 19:30:44
     **/ 
    List<AppointmentRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-30 19:30:44
     **/ 
    int selectCount(Map<Object, Object> param);
}