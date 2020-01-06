package com.zh.module.service;

import com.zh.module.entity.SmsRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-06 15:21:46
 **/ 
public interface SmsRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:21:46
     **/ 
    int insert(SmsRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:21:46
     **/ 
    int insertSelective(SmsRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:21:46
     **/ 
    int updateByPrimaryKey(SmsRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:21:46
     **/ 
    int updateByPrimaryKeySelective(SmsRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:21:46
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:21:46
     **/ 
    SmsRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:21:46
     **/ 
    List<SmsRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:21:46
     **/ 
    List<SmsRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-06 15:21:46
     **/ 
    int selectCount(Map<Object, Object> param);
    List<SmsRecord> queryListByTimeLimit(Map map);
    SmsRecord getByIdAndPhone(Integer codeId, String phone);
}