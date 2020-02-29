package com.zh.module.biz.impl;

import com.zh.module.biz.PetsMatchingListBiz;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.Pets;
import com.zh.module.entity.PetsList;
import com.zh.module.entity.PetsMatchingList;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PageModel;
import com.zh.module.model.PetsMatchingListModel;
import com.zh.module.service.*;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
@Transactional
public class PetsMatchingListListBizImpl implements PetsMatchingListBiz {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String list(Users users, Integer state, PageModel pageModel) {
        Map<Object, Object> param = new HashMap<>();
        param.put("state", state);
        param.put("userId", users.getId());
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<Map<String, Object>> lists = petsMatchingListService.selectListPaging(param);
        List<PetsMatchingListModel> listModels = new LinkedList<>();
        String appointmentTime;
        String appStartTime;
        String appEndTime;
        BigDecimal price;
        String PetsNumber = "";
        String days = "";
        BigDecimal numbers;
        for(Map<String, Object> map : lists){
            PetsMatchingListModel petsMatchingListModel = new PetsMatchingListModel();
            petsMatchingListModel.setId((Integer) map.get("id"));
            petsMatchingListModel.setImgUrl(map.get("img_url").toString());
            PetsNumber = map.get("pets_number") == null ? "" : map.get("pets_number").toString();
            petsMatchingListModel.setNumber(PetsNumber);
            petsMatchingListModel.setName(map.get("name").toString());
            price = map.get("price") == null ? BigDecimal.ZERO : new BigDecimal(map.get("price").toString());
            petsMatchingListModel.setPrice(price);
            int statePml = (Integer) map.get("state");
            petsMatchingListModel.setState((Integer) map.get("state"));
            if(state == 0){
                appStartTime =map.get("appointment_start_time").toString();
                petsMatchingListModel.setAppointmentStartTime(appStartTime);
                appEndTime = map.get("appointment_end_time").toString();
                petsMatchingListModel.setAppointmentEndTime(appEndTime);
                int time;
                if(statePml == GlobalParams.PET_MATCHING_STATE_NOPAY) {
                    time = DateUtils.secondBetween(appEndTime);
                }else{
                    appEndTime = map.get("inactive_time").toString();
                    time = DateUtils.secondBetween(appEndTime);
                }
                petsMatchingListModel.setInactiveTime(String.valueOf(-time));
            }else{
                appointmentTime =map.get("start_time").toString();
                petsMatchingListModel.setAppointmentTime(appointmentTime);
                petsMatchingListModel.setProfited(price.multiply(new BigDecimal(map.get("profit_rate").toString()).setScale(2, BigDecimal.ROUND_HALF_UP)));
            }
            days = map.get("profit_days") == null ? "0" : map.get("profit_days").toString();
            numbers = map.get("profit_rate") == null ? BigDecimal.ZERO : new BigDecimal(map.get("profit_rate").toString());
            petsMatchingListModel.setProfit(days + "天/" + numbers.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            listModels.add(petsMatchingListModel);
        }
        return Result.toResult(ResultCode.SUCCESS, listModels);
    }
}