package com.zh.module.service.impl;

import com.zh.module.dao.TeamRecordMapper;
import com.zh.module.entity.TeamRecord;
import com.zh.module.service.TeamRecordService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-03 14:06:58
 **/ 
@Service("teamRecordService")
public class TeamRecordServiceImpl implements TeamRecordService {
    @Resource
    private TeamRecordMapper teamRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(TeamRecordServiceImpl.class);

    @Override
    public int insert(TeamRecord record) {
        return this.teamRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(TeamRecord record) {
        return this.teamRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(TeamRecord record) {
        return this.teamRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(TeamRecord record) {
        return this.teamRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.teamRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TeamRecord selectByPrimaryKey(Integer id) {
        return this.teamRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TeamRecord> selectAll(Map<Object, Object> param) {
        return this.teamRecordMapper.selectAll(param);
    }

    @Override
    public List<TeamRecord> selectPaging(Map<Object, Object> param) {
        return this.teamRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.teamRecordMapper.selectCount(param);
    }
}