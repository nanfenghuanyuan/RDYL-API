package com.zh.module.dao;

import com.zh.module.entity.PetsList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PetsListMapper {
    int insert(PetsList record);

    int insertSelective(PetsList record);

    int updateByPrimaryKey(PetsList record);

    int updateByPrimaryKeySelective(PetsList record);

    int deleteByPrimaryKey(Integer id);

    PetsList selectByPrimaryKey(Integer id);

    List<PetsList> selectAll(Map<Object, Object> param);

    List<PetsList> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<PetsList> selectDoProfit(Map<Object, Object> param);

    List<Map<String, Object>> selectListPaging(Map<Object, Object> param);

    String selectSumAmountByUser(Integer id);

    List<PetsList> selectDoBuy(Map<Object, Object> param);

    List<PetsList> selectToDayList(@Param("start") String start, @Param("end") String end);
}