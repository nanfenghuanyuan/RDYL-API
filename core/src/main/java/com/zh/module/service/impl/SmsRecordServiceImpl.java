package com.zh.module.service.impl;

import com.zh.module.dao.SmsRecordMapper;
import com.zh.module.entity.SmsRecord;
import com.zh.module.service.SmsRecordService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-20 16:11:29
 **/ 
@Service("smsRecordService")
public class SmsRecordServiceImpl implements SmsRecordService {
    @Resource
    private SmsRecordMapper smsRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(SmsRecordServiceImpl.class);

    @Override
    public int insert(SmsRecord record) {
        return this.smsRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(SmsRecord record) {
        return this.smsRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(SmsRecord record) {
        return this.smsRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(SmsRecord record) {
        return this.smsRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.smsRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SmsRecord selectByPrimaryKey(Integer id) {
        return this.smsRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsRecord> selectAll(Map<Object, Object> param) {
        return this.smsRecordMapper.selectAll(param);
    }

    @Override
    public List<SmsRecord> selectPaging(Map<Object, Object> param) {
        return this.smsRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.smsRecordMapper.selectCount(param);
    }

    @Override
    public SmsRecord getByIdAndPhone(Integer codeId, String phone) {
        Map<Object, Object> params = new HashMap<>();
        params.put("id", codeId);
        params.put("phone", phone);
        List<SmsRecord> list = selectAll(params);
        return list == null || list.isEmpty() ? null:list.get(0);
    }
}