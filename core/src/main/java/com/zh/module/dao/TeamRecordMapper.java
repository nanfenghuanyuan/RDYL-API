package com.zh.module.dao;

import com.zh.module.entity.TeamRecord;
import java.util.List;
import java.util.Map;

public interface TeamRecordMapper {
    int insert(TeamRecord record);

    int insertSelective(TeamRecord record);

    int updateByPrimaryKey(TeamRecord record);

    int updateByPrimaryKeySelective(TeamRecord record);

    int deleteByPrimaryKey(Integer id);

    TeamRecord selectByPrimaryKey(Integer id);

    List<TeamRecord> selectAll(Map<Object, Object> param);

    List<TeamRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}