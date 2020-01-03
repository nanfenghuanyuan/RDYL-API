package com.zh.module.service;

import com.zh.module.entity.TeamAwardRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-03 14:07:23
 **/ 
public interface TeamAwardRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-03 14:07:23
     **/ 
    int insert(TeamAwardRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-03 14:07:23
     **/ 
    int insertSelective(TeamAwardRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-03 14:07:23
     **/ 
    int updateByPrimaryKey(TeamAwardRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-03 14:07:23
     **/ 
    int updateByPrimaryKeySelective(TeamAwardRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-03 14:07:23
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-03 14:07:23
     **/ 
    TeamAwardRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-03 14:07:23
     **/ 
    List<TeamAwardRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-03 14:07:23
     **/ 
    List<TeamAwardRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-03 14:07:23
     **/ 
    int selectCount(Map<Object, Object> param);

    TeamAwardRecord selectByUserId(Integer id);

    void updateOrInsert(TeamAwardRecord teamAwardRecord);
}