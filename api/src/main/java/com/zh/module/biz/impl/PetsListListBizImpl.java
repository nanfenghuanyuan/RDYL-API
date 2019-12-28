package com.zh.module.biz.impl;

import com.zh.module.biz.PetsListBiz;
import com.zh.module.biz.PetsMatchingListBiz;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.PageModel;
import com.zh.module.model.PayInfoModel;
import com.zh.module.model.PetsMatchingListModel;
import com.zh.module.model.PetsOrderModel;
import com.zh.module.service.*;
import com.zh.module.utils.DateUtils;
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
public class PetsListListBizImpl implements PetsListBiz {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String list(Users users, Integer state, PageModel pageModel) {
        Map<Object, Object> param = new HashMap<>();
        param.put("state", state);
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<Map<String, Object>> lists = petsListService.selectListPaging(param);
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
            time = DateUtils.getDateFormate((Date) map.get("create_time"));
            petsMatchingListModel.setTime(time);
            petsMatchingListModel.setProfited(price.multiply(new BigDecimal(map.get("profit_rate").toString()).setScale(2, BigDecimal.ROUND_HALF_UP)));
            petsMatchingListModel.setProfit(map.get("profit_days").toString() + "天/" + new BigDecimal(map.get("profit_rate").toString()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            listModels.add(petsMatchingListModel);
        }
        return Result.toResult(ResultCode.SUCCESS, listModels);
    }

    @Override
    public String get(Users users, Integer id) {
        PetsMatchingList petsMatchingList = petsMatchingListService.selectByPetListIdAndActive(id);
        if(petsMatchingList == null){
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }
        boolean isBuyer = false;
        Pets pets = petsService.selectByLevel(petsMatchingList.getLevel().intValue());
        PetsList petsList = petsListService.selectByPrimaryKey(petsMatchingList.getPetListId().intValue());
        if(users.getId().equals(petsMatchingList.getBuyUserId())){
            isBuyer = true;
        }
        PetsOrderModel petsOrderModel = new PetsOrderModel();

        List<BindInfo> bindInfos = new ArrayList<>();
        List<PayInfoModel> payInfoModels = new ArrayList<>();
        if(isBuyer) {
            Users saleUser = usersService.selectByPrimaryKey(petsMatchingList.getSaleUserId());
            petsOrderModel.setBuyName(users.getNickName());
            petsOrderModel.setBuyPhone(users.getPhone());
            petsOrderModel.setSaleName(saleUser.getNickName());
            petsOrderModel.setSalePhone(saleUser.getPhone());
            bindInfos = bindInfoService.queryByUser(petsMatchingList.getSaleUserId());
        }else{
            Users saleUser = usersService.selectByPrimaryKey(petsMatchingList.getBuyUserId());
            petsOrderModel.setBuyName(saleUser.getNickName());
            petsOrderModel.setBuyPhone(saleUser.getPhone());
            petsOrderModel.setSaleName(users.getNickName());
            petsOrderModel.setSalePhone(users.getPhone());
            bindInfos = bindInfoService.queryByUser(users.getId());
        }
        for(BindInfo bindInfo : bindInfos){
            PayInfoModel payInfoModel = new PayInfoModel();
            payInfoModel.setAccount(bindInfo.getAccount());
            payInfoModel.setImgUrl(bindInfo.getImgUrl());
            payInfoModel.setName(bindInfo.getName());
            payInfoModel.setType(bindInfo.getType().intValue());
            payInfoModel.setBankName(bindInfo.getBankName());
            payInfoModel.setBranchName(bindInfo.getBranchName());
            payInfoModels.add(payInfoModel);
        }
        petsOrderModel.setPayInfoModels(payInfoModels);
        petsOrderModel.setName(pets.getName());
        petsOrderModel.setPrice(petsList.getPrice());
        petsOrderModel.setProfit(petsList.getProfitDays() + "天/" + new BigDecimal(petsList.getProfitRate().toString()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
        petsOrderModel.setTransTime(DateUtils.getDateFormate(petsList.getCreateTime()));
        petsOrderModel.setPayTime(petsMatchingList.getPayTime());
        return Result.toResult(ResultCode.SUCCESS, petsOrderModel);
    }
}