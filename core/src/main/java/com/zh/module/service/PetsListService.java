package com.zh.module.service;

import com.zh.module.entity.PetsList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2020-01-27 18:15:25
 **/ 
public interface PetsListService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-27 18:15:25
     **/ 
    int insert(PetsList record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2020-01-27 18:15:25
     **/ 
    int insertSelective(PetsList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-27 18:15:25
     **/ 
    int updateByPrimaryKey(PetsList record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2020-01-27 18:15:25
     **/ 
    int updateByPrimaryKeySelective(PetsList record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2020-01-27 18:15:25
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2020-01-27 18:15:25
     **/ 
    PetsList selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2020-01-27 18:15:25
     **/ 
    List<PetsList> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2020-01-27 18:15:25
     **/ 
    List<PetsList> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2020-01-27 18:15:25
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 收益结算列表
     * @param param
     * @return
     */
    List<PetsList> selectDoProfit(Map<Object, Object> param);

    List<Map<String, Object>> selectListPaging(Map<Object, Object> param);

    /**
     * 统计用户名下总资产
     * @param id
     * @return
     */
    String selectSumAmountByUser(Integer id);

    /**
     * 可购买宠物i列表
     * @param param
     * @return
     */
    List<PetsList> selectDoBuy(Map<Object, Object> param);
}