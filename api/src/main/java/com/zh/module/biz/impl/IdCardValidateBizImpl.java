package com.zh.module.biz.impl;

import com.zh.module.biz.IdCardValidateBiz;
import com.zh.module.entity.IdcardValidate;
import com.zh.module.service.IdcardValidateService;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.TimeStampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
@Service
@Transactional
public class IdCardValidateBizImpl implements IdCardValidateBiz {

    @Autowired
    private IdcardValidateService idcardValidateService;
    @Override
    public Map<String, Object> queryValidateTimes(Integer userId,Integer dateMis) {
        String currDate = DateUtils.getCurrentTimeStr();
        String  startDate = DateUtils.getSomeDay(-dateMis);
        String sql = "SELECT COUNT(*) as counts, DATE_FORMAT(createTime, '%Y-%m-%d') as valiDate  FROM t_idcard_validate WHERE userId=:userId and createTime between :startDate and :currDate GROUP BY  valiDate";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startDate", TimeStampUtils.StrToTimeStamp(startDate));
        map.put("currDate", TimeStampUtils.StrToTimeStamp(currDate));
        List<?> results =  idcardValidateService.queryValidateTimes(map);

        if(results!=null&&!results.isEmpty()){
            Map<String, Object> countMap = new HashMap<>();
            for(Object res :results){
                Map<String, Object> data =(Map<String, Object>)res;
                countMap.put((String)data.get("valiDate"), data.get("counts"));
            }
            return countMap;

        }else{
            return null;
        }
    }

    @Override
    public Integer getByUserByIdcard(String identificationnumber) {
        Map<Object, Object> map = new HashMap<>();
        map.put("identificationnumber", identificationnumber);
        map.put("state", 1);
        List<IdcardValidate> idcardValidates = this.idcardValidateService.selectAll(map);
        if(idcardValidates.size() == 0){
           return null;
        }
        IdcardValidate idcardValidate = idcardValidates.get(0);
        return idcardValidate.getUserId();
    }

    @Override
    public void insert(IdcardValidate iv) {
        this.idcardValidateService.insertSelective(iv);
    }

    @Override
    public IdcardValidate selectByFaceId(String faceId) {
        return idcardValidateService.queryByTaskId(faceId);
    }
}
