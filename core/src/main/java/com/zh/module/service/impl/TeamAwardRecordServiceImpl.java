package com.zh.module.service.impl;

import com.zh.module.dao.TeamAwardRecordMapper;
import com.zh.module.entity.TeamAwardRecord;
import com.zh.module.service.TeamAwardRecordService;

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
 * @date: 2020-01-03 14:07:23
 **/ 
@Service("teamAwardRecordService")
public class TeamAwardRecordServiceImpl implements TeamAwardRecordService {
    @Resource
    private TeamAwardRecordMapper teamAwardRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(TeamAwardRecordServiceImpl.class);

    @Override
    public int insert(TeamAwardRecord record) {
        return this.teamAwardRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(TeamAwardRecord record) {
        return this.teamAwardRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(TeamAwardRecord record) {
        return this.teamAwardRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(TeamAwardRecord record) {
        return this.teamAwardRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.teamAwardRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TeamAwardRecord selectByPrimaryKey(Integer id) {
        return this.teamAwardRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TeamAwardRecord> selectAll(Map<Object, Object> param) {
        return this.teamAwardRecordMapper.selectAll(param);
    }

    @Override
    public List<TeamAwardRecord> selectPaging(Map<Object, Object> param) {
        return this.teamAwardRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.teamAwardRecordMapper.selectCount(param);
    }

    @Override
    public TeamAwardRecord selectByUserId(Integer id) {
        Map<Object, Object> param = new HashMap<>();
        param.put("userId", id);
        List<TeamAwardRecord> list = this.teamAwardRecordMapper.selectAll(param);
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public void updateOrInsert(TeamAwardRecord teamAwardRecord) {
        if(teamAwardRecord.getId() == null){
            this.teamAwardRecordMapper.insertSelective(teamAwardRecord);
        }else{
            this.teamAwardRecordMapper.updateByPrimaryKeySelective(teamAwardRecord);
        }
    }
}