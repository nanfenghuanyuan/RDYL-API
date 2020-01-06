package com.zh.module.service.impl;

import com.zh.module.dao.SysparamsMapper;
import com.zh.module.entity.Sysparams;
import com.zh.module.service.SysparamsService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.zh.module.utils.RedisUtil;
import com.zh.module.variables.RedisKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-06 15:27:03
 **/ 
@Service("sysparamsService")
public class SysparamsServiceImpl implements SysparamsService {
    @Resource
    private SysparamsMapper sysparamsMapper;
    @Resource
    private RedisTemplate<String, String> redis;

    private static final Logger logger = LoggerFactory.getLogger(SysparamsServiceImpl.class);

    @Override
    public int insert(Sysparams record) {
        return this.sysparamsMapper.insert(record);
    }

    @Override
    public int insertSelective(Sysparams record) {
        return this.sysparamsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Sysparams record) {
        return this.sysparamsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Sysparams record) {
        return this.sysparamsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.sysparamsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Sysparams selectByPrimaryKey(Integer id) {
        return this.sysparamsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Sysparams> selectAll(Map<Object, Object> param) {
        return this.sysparamsMapper.selectAll(param);
    }

    @Override
    public List<Sysparams> selectPaging(Map<Object, Object> param) {
        return this.sysparamsMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.sysparamsMapper.selectCount(param);
    }
    @Override
    public Sysparams getValByKey(String key) {
        String redisKey = String.format(RedisKey.SYSTEM_PARAM, key);
        Sysparams systemParam = RedisUtil.searchStringObj(redis, redisKey, Sysparams.class);
        if(systemParam == null){
            systemParam = sysparamsMapper.selectByKey(key);
            if(systemParam!=null){
                RedisUtil.addStringObj(redis, redisKey, systemParam);
            }
            return systemParam;
        }else{
            return systemParam;
        }
    }
    @Override
    public String getValStringByKey(String key) {
        Sysparams param = getValByKey(key);
        if(param == null) {
            return "";
        }
        return param.getKeyval();
    }

}