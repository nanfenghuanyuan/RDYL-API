package com.zh.module.service;

import com.zh.module.entity.AccountTransfer;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-13 20:59:11
 **/ 
public interface AccountTransferService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-13 20:59:11
     **/ 
    int insert(AccountTransfer record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-13 20:59:11
     **/ 
    int insertSelective(AccountTransfer record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-13 20:59:11
     **/ 
    int updateByPrimaryKey(AccountTransfer record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-13 20:59:11
     **/ 
    int updateByPrimaryKeySelective(AccountTransfer record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-13 20:59:11
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-13 20:59:11
     **/ 
    AccountTransfer selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-13 20:59:11
     **/ 
    List<AccountTransfer> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-13 20:59:11
     **/ 
    List<AccountTransfer> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-13 20:59:11
     **/ 
    int selectCount(Map<Object, Object> param);
}