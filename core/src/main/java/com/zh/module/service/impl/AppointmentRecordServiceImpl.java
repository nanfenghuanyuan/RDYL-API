package com.zh.module.service.impl;

import com.zh.module.dao.AppointmentRecordMapper;
import com.zh.module.entity.AppointmentRecord;
import com.zh.module.service.AppointmentRecordService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-13 11:31:17
 **/ 
@Service("appointmentRecordService")
public class AppointmentRecordServiceImpl implements AppointmentRecordService {
    @Resource
    private AppointmentRecordMapper appointmentRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentRecordServiceImpl.class);

    @Override
    public int insert(AppointmentRecord record) {
        return this.appointmentRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(AppointmentRecord record) {
        return this.appointmentRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(AppointmentRecord record) {
        return this.appointmentRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AppointmentRecord record) {
        return this.appointmentRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.appointmentRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AppointmentRecord selectByPrimaryKey(Integer id) {
        return this.appointmentRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AppointmentRecord> selectAll(Map<Object, Object> param) {
        return this.appointmentRecordMapper.selectAll(param);
    }

    @Override
    public List<AppointmentRecord> selectPaging(Map<Object, Object> param) {
        return this.appointmentRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.appointmentRecordMapper.selectCount(param);
    }
}