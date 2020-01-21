package com.zh.module.service.impl;

import com.zh.module.dao.FlowMapper;
import com.zh.module.entity.Flow;
import com.zh.module.service.FlowService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-25 17:52:13
 **/ 
@Service("flowService")
public class FlowServiceImpl implements FlowService {
    @Resource
    private FlowMapper flowMapper;

    private static final Logger logger = LoggerFactory.getLogger(FlowServiceImpl.class);

    @Override
    public int insert(Flow record) {
        return this.flowMapper.insert(record);
    }

    @Override
    public int insertSelective(Flow record) {
        return this.flowMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Flow record) {
        return this.flowMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Flow record) {
        return this.flowMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.flowMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Flow selectByPrimaryKey(Integer id) {
        return this.flowMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Flow> selectAll(Map<Object, Object> param) {
        return this.flowMapper.selectAll(param);
    }

    @Override
    public List<Flow> selectPaging(Map<Object, Object> param) {
        return this.flowMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.flowMapper.selectCount(param);
    }

    @Override
    public String selectPersonProfitSumAmount(Integer userId, String type) {
        return this.flowMapper.selectPersonProfitSumAmount(userId, type);
    }
}