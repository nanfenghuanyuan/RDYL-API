package com.zh.module.service.impl;

import com.zh.module.dao.IdcardValidateMapper;
import com.zh.module.entity.IdcardValidate;
import com.zh.module.service.IdcardValidateService;

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
 * @date: 2020-01-06 15:17:34
 **/ 
@Service("idcardValidateService")
public class IdcardValidateServiceImpl implements IdcardValidateService {
    @Resource
    private IdcardValidateMapper idcardValidateMapper;

    private static final Logger logger = LoggerFactory.getLogger(IdcardValidateServiceImpl.class);

    @Override
    public int insert(IdcardValidate record) {
        return this.idcardValidateMapper.insert(record);
    }

    @Override
    public int insertSelective(IdcardValidate record) {
        return this.idcardValidateMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(IdcardValidate record) {
        return this.idcardValidateMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(IdcardValidate record) {
        return this.idcardValidateMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.idcardValidateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public IdcardValidate selectByPrimaryKey(Integer id) {
        return this.idcardValidateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<IdcardValidate> selectAll(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectAll(param);
    }

    @Override
    public List<IdcardValidate> selectPaging(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectCount(param);
    }
    @Override
    public List<?> queryValidateTimes(Map<String, Object> map) {
        return this.idcardValidateMapper.queryValidateTimes(map);
    }

    @Override
    public IdcardValidate queryByTaskId(String taskId) {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("taskid", taskId);
        List<IdcardValidate> list = this.idcardValidateMapper.selectAll(params);
        return list == null || list.isEmpty() ? null :list.get(0);
    }

    @Override
    public List<Map<String, Object>> selectConditionPaging(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectConditionPaging(param);
    }

    @Override
    public int selectConditionCount(Map<Object, Object> param) {
        return this.idcardValidateMapper.selectConditionCount(param);
    }
}