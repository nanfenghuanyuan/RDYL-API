package com.zh.module.dao;

import com.zh.module.entity.AppointmentRecord;
import java.util.List;
import java.util.Map;

public interface AppointmentRecordMapper {
    int insert(AppointmentRecord record);

    int insertSelective(AppointmentRecord record);

    int updateByPrimaryKey(AppointmentRecord record);

    int updateByPrimaryKeySelective(AppointmentRecord record);

    int deleteByPrimaryKey(Integer id);

    AppointmentRecord selectByPrimaryKey(Integer id);

    List<AppointmentRecord> selectAll(Map<Object, Object> param);

    List<AppointmentRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}