package com.zh.module.service;

import com.zh.module.entity.Notice;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-25 11:50:17
 **/ 
public interface NoticeService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 11:50:17
     **/ 
    int insert(Notice record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-25 11:50:17
     **/ 
    int insertSelective(Notice record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 11:50:17
     **/ 
    int updateByPrimaryKey(Notice record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-25 11:50:17
     **/ 
    int updateByPrimaryKeySelective(Notice record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-25 11:50:17
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 11:50:17
     **/ 
    Notice selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 11:50:17
     **/ 
    List<Notice> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 11:50:17
     **/ 
    List<Notice> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-25 11:50:17
     **/ 
    int selectCount(Map<Object, Object> param);

    Notice seletByStart();
}