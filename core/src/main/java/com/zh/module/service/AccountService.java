package com.zh.module.service;

import com.zh.module.entity.Account;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-20 18:18:17
 **/ 
public interface AccountService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int insert(Account record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int insertSelective(Account record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int updateByPrimaryKey(Account record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int updateByPrimaryKeySelective(Account record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    Account selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    List<Account> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    List<Account> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-20 18:18:17
     **/ 
    int selectCount(Map<Object, Object> param);
}