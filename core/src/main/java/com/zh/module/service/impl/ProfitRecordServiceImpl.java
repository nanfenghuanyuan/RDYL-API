package com.zh.module.service.impl;

import com.zh.module.dao.ProfitRecordMapper;
import com.zh.module.entity.ProfitRecord;
import com.zh.module.service.ProfitRecordService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-30 18:10:12
 **/ 
@Service("profitRecordService")
public class ProfitRecordServiceImpl implements ProfitRecordService {
    @Resource
    private ProfitRecordMapper profitRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProfitRecordServiceImpl.class);

    @Override
    public int insert(ProfitRecord record) {
        return this.profitRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(ProfitRecord record) {
        return this.profitRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProfitRecord record) {
        return this.profitRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ProfitRecord record) {
        return this.profitRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.profitRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProfitRecord selectByPrimaryKey(Integer id) {
        return this.profitRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProfitRecord> selectAll(Map<Object, Object> param) {
        return this.profitRecordMapper.selectAll(param);
    }

    @Override
    public List<ProfitRecord> selectPaging(Map<Object, Object> param) {
        return this.profitRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.profitRecordMapper.selectCount(param);
    }

    @Override
    public String selectSumAmount(Integer userId, int type) {
        return this.profitRecordMapper.selectSumAmount(userId, type);
    }

    @Override
    public String selectCountByTime(String start, String end) {
        return this.profitRecordMapper.selectCountByTime(start, end);
    }
}