package com.zh.module.dao;

import com.zh.module.entity.TeamAwardRecord;
import java.util.List;
import java.util.Map;

public interface TeamAwardRecordMapper {
    int insert(TeamAwardRecord record);

    int insertSelective(TeamAwardRecord record);

    int updateByPrimaryKey(TeamAwardRecord record);

    int updateByPrimaryKeySelective(TeamAwardRecord record);

    int deleteByPrimaryKey(Integer id);

    TeamAwardRecord selectByPrimaryKey(Integer id);

    List<TeamAwardRecord> selectAll(Map<Object, Object> param);

    List<TeamAwardRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}