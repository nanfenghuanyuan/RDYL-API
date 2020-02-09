package com.zh.module.service;

import com.zh.module.entity.Users;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-05 22:31:57
 **/ 
public interface UsersService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-05 22:31:57
     **/ 
    int insert(Users record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-05 22:31:57
     **/ 
    int insertSelective(Users record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-05 22:31:57
     **/ 
    int updateByPrimaryKey(Users record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-05 22:31:57
     **/ 
    int updateByPrimaryKeySelective(Users record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-05 22:31:57
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-05 22:31:57
     **/ 
    Users selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-05 22:31:57
     **/ 
    List<Users> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-05 22:31:57
     **/ 
    List<Users> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-05 22:31:57
     **/ 
    int selectCount(Map<Object, Object> param);

    Users selectByPhone(String phone);

    Users selectByUUID(String uuid);

    List<Users> selectByUUIDList(String uuid);

    List<Users> selectByReferID(String referId);

    List<Users> selectByReferIDAndActive(String uuid);
}