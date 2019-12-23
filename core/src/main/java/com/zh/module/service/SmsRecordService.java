package com.zh.module.service;

import com.zh.module.entity.SmsRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-20 16:11:29
 **/ 
public interface SmsRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:11:29
     **/ 
    int insert(SmsRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:11:29
     **/ 
    int insertSelective(SmsRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:11:29
     **/ 
    int updateByPrimaryKey(SmsRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:11:29
     **/ 
    int updateByPrimaryKeySelective(SmsRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:11:29
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:11:29
     **/ 
    SmsRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:11:29
     **/ 
    List<SmsRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:11:29
     **/ 
    List<SmsRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 16:11:29
     **/ 
    int selectCount(Map<Object, Object> param);

    SmsRecord getByIdAndPhone(Integer codeId, String phone);
}