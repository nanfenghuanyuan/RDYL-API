package com.zh.module.service.impl;

import com.zh.module.dao.UsersMapper;
import com.zh.module.entity.Users;
import com.zh.module.service.UsersService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-05 22:31:57
 **/ 
@Service("usersService")
public class UsersServiceImpl implements UsersService {
    @Resource
    private UsersMapper usersMapper;

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Override
    public int insert(Users record) {
        return this.usersMapper.insert(record);
    }

    @Override
    public int insertSelective(Users record) {
        return this.usersMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Users record) {
        return this.usersMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Users record) {
        return this.usersMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.usersMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Users selectByPrimaryKey(Integer id) {
        return this.usersMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Users> selectAll(Map<Object, Object> param) {
        return this.usersMapper.selectAll(param);
    }

    @Override
    public List<Users> selectPaging(Map<Object, Object> param) {
        return this.usersMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.usersMapper.selectCount(param);
    }

    @Override
    public Users selectByUUID(String uuid) {
        Map<Object, Object> map = new HashMap();
        map.put("uuid", uuid);
        List<Users> users = selectAll(map);
        return users == null || users.isEmpty() ? null : users.get(0);
    }
    @Override
    public Users selectByPhone(String phone) {
        Map<Object, Object> map = new HashMap();
        map.put("phone", phone);
        List<Users> users = selectAll(map);

        return users == null|| users.isEmpty() ? null : users.get(0);
    }

    @Override
    public Users selectByReferID(String referId) {
        Map<Object, Object> map = new HashMap();
        map.put("referId", referId);
        List<Users> users = selectAll(map);
        return users == null || users.isEmpty() ? null : users.get(0);
    }
}