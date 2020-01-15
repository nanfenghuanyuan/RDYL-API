package com.zh.module.biz;

import com.zh.module.entity.IdcardValidate;

import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
public interface IdCardValidateBiz {
    /**
     * 实名认证校验次数
     * @param userId
     * @return Map<String,Object>
     */
    Map<String,Object> queryValidateTimes(Integer userId, Integer dateMis);

    Integer getByUserByIdcard(String identificationnumber);

    void insert(IdcardValidate iv);

    IdcardValidate selectByFaceId(String faceId);
}
