package com.zh.module.service.impl;

import com.zh.module.dao.CoinManagerMapper;
import com.zh.module.entity.CoinManager;
import com.zh.module.service.CoinManagerService;

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
 * @date: 2019-12-31 16:40:21
 **/ 
@Service("coinManagerService")
public class CoinManagerServiceImpl implements CoinManagerService {
    @Resource
    private CoinManagerMapper coinManagerMapper;

    private static final Logger logger = LoggerFactory.getLogger(CoinManagerServiceImpl.class);

    @Override
    public int insert(CoinManager record) {
        return this.coinManagerMapper.insert(record);
    }

    @Override
    public int insertSelective(CoinManager record) {
        return this.coinManagerMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(CoinManager record) {
        return this.coinManagerMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(CoinManager record) {
        return this.coinManagerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.coinManagerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CoinManager selectByPrimaryKey(Integer id) {
        return this.coinManagerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CoinManager> selectAll(Map<Object, Object> param) {
        return this.coinManagerMapper.selectAll(param);
    }

    @Override
    public List<CoinManager> selectPaging(Map<Object, Object> param) {
        return this.coinManagerMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.coinManagerMapper.selectCount(param);
    }

    @Override
    public CoinManager queryByCoinType(int coinType) {
        Map<Object,Object> map = new HashMap<>();
        map.put("coinType",coinType);
        List<CoinManager> coinManages = selectAll(map);
        return coinManages.size() == 0 ? null : coinManages.get(0);
    }
}