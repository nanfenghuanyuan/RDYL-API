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
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<Map<String, Object>> lists = petsMatchingListService.selectListPaging(param);
        String cancelTime = sysparamsService.getValStringByKey(SystemParams.PETS_MATCHING_CANCEL_TIME);
        List<PetsMatchingListModel> listModels = new LinkedList<>();
        String time;
        BigDecimal price;
        for(Map<String, Object> map : lists){
            PetsMatchingListModel petsMatchingListModel = new PetsMatchingListModel();
            petsMatchingListModel.setImgUrl(map.get("img_url").toString());
            petsMatchingListModel.setName(map.get("name").toString());
            price = new BigDecimal(map.get("price").toString());
            petsMatchingListModel.setPrice(price);
            petsMatchingListModel.setState(state);
            time = DateUtils.getDateFormate((Date) map.get("update_time"));
            petsMatchingListModel.setTime(time);
            petsMatchingListModel.setEndTime(DateUtils.getSomeMinutes(Integer.parseInt(cancelTime), (Date) map.get("update_time")));
            petsMatchingListModel.setProfited(price.multiply(new BigDecimal(map.get("profit_rate").toString()).setScale(2, BigDecimal.ROUND_HALF_UP)));
            petsMatchingListModel.setProfit(map.get("profit_days").toString() + "天/" + new BigDecimal(map.get("profit_rate").toString()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            listModels.add(petsMatchingListModel);
        }
        return Result.toResult(ResultCode.SUCCESS, listModels);
    }
}