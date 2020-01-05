package com.zh.module.service;

import com.zh.module.entity.TeamRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-05 20:11:56
 **/ 
public interface TeamRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-05 20:11:56
     **/ 
    int insert(TeamRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-05 20:11:56
     **/ 
    int insertSelective(TeamRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-05 20:11:56
     **/ 
    int updateByPrimaryKey(TeamRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-05 20:11:56
     **/ 
    int updateByPrimaryKeySelective(TeamRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-05 20:11:56
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-05 20:11:56
     **/ 
    TeamRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-05 20:11:56
     **/ 
    List<TeamRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-05 20:11:56
     **/ 
    List<TeamRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-05 20:11:56
     **/ 
    int selectCount(Map<Object, Object> param);
}