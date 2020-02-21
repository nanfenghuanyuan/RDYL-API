package com.zh.module.service;

import com.zh.module.entity.WithdrawQuote;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-02-21 14:01:13
 **/ 
public interface WithdrawQuoteService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-02-21 14:01:13
     **/ 
    int insert(WithdrawQuote record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-02-21 14:01:13
     **/ 
    int insertSelective(WithdrawQuote record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-02-21 14:01:13
     **/ 
    int updateByPrimaryKey(WithdrawQuote record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-02-21 14:01:13
     **/ 
    int updateByPrimaryKeySelective(WithdrawQuote record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-02-21 14:01:13
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-02-21 14:01:13
     **/ 
    WithdrawQuote selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-02-21 14:01:13
     **/ 
    List<WithdrawQuote> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-02-21 14:01:13
     **/ 
    List<WithdrawQuote> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-02-21 14:01:13
     **/ 
    int selectCount(Map<Object, Object> param);

    WithdrawQuote selectByUser(Integer userId);
}