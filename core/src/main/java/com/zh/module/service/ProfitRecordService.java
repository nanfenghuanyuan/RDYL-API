package com.zh.module.service;

import com.zh.module.entity.ProfitRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-30 18:10:12
 **/ 
public interface ProfitRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-30 18:10:12
     **/ 
    int insert(ProfitRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-30 18:10:12
     **/ 
    int insertSelective(ProfitRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-30 18:10:12
     **/ 
    int updateByPrimaryKey(ProfitRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-30 18:10:12
     **/ 
    int updateByPrimaryKeySelective(ProfitRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-30 18:10:12
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-30 18:10:12
     **/ 
    ProfitRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-30 18:10:12
     **/ 
    List<ProfitRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-30 18:10:12
     **/ 
    List<ProfitRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-30 18:10:12
     **/ 
    int selectCount(Map<Object, Object> param);

    String selectSumAmount(Integer userId, int type);

    String selectCountByTime(String start, String end);
}