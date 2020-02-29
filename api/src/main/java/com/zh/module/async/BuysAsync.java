package com.zh.module.async;

import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.entity.Pets;
import com.zh.module.entity.PetsList;
import com.zh.module.entity.PetsMatchingList;
import com.zh.module.entity.Sysparams;
import com.zh.module.service.*;
import com.zh.module.utils.BigDecimalUtils;
import com.zh.module.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Component
public class BuysAsync {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AppointmentRecordService appointmentRecordService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private BlackListService blackListService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Async
    public void buys(Pets pets, PetsList petsList, Integer userId){
        Integer saleUserId = petsList.getUserId();

        //验证是否已存在预约记录
        int count = checkMatchingRecord(userId, petsList.getLevel().intValue());
        BigDecimal appointmentAmount;

        /*设置失效时间*/
        int interval = 10;
        Sysparams param1 = sysparamsService.getValByKey(SystemParams.PETS_MATCHING_NO_PAY_CANCEL_TIME);
        if(param1 != null){
            interval = Integer.parseInt(param1.getKeyval());
        }
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MINUTE, interval);
        Date inactiveTime = new Timestamp(current.getTimeInMillis());

        //没有预约
        if (count == 0) {
            appointmentAmount = pets.getPayAmount();
            PetsMatchingList petsMatchingList = new PetsMatchingList();
            petsMatchingList.setLevel(petsList.getLevel());
            petsMatchingList.setAmount(appointmentAmount);
            petsMatchingList.setBuyUserId(userId);
            petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_NOPAY);
            petsMatchingList.setPetListId(petsList.getId());
            petsMatchingList.setSaleUserId(saleUserId);
            petsMatchingList.setInactiveTime(inactiveTime);
            petsMatchingList.setAppointmentStartTime(DateUtils.getCurrentTimeStr());
            petsMatchingList.setAppointmentEndTime(DateUtils.getDateFormate(inactiveTime));
            petsMatchingListService.insertSelective(petsMatchingList);

            accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, BigDecimalUtils.plusMinus(appointmentAmount), BigDecimal.ZERO, userId, "领养消耗", petsMatchingList.getId());
        } else {
            Map<Object, Object> param = new HashMap<>();
            param.put("level", petsList.getLevel());
            param.put("petListId", "-1");
            param.put("buyUserId", userId);
            param.put("state", GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
            List<PetsMatchingList> petsMatchingLists = petsMatchingListService.selectAll(param);
            PetsMatchingList petsMatchingList =  petsMatchingLists == null || petsMatchingLists.size() == 0 ? null : petsMatchingLists.get(0);
            if (petsMatchingList != null) {
                petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_NOPAY);
                petsMatchingList.setPetListId(petsList.getId());
                petsMatchingList.setSaleUserId(saleUserId);
                petsMatchingList.setInactiveTime(inactiveTime);
                petsMatchingList.setAppointmentStartTime(DateUtils.getCurrentTimeStr());
                petsMatchingList.setAppointmentEndTime(DateUtils.getDateFormate(inactiveTime));
                petsMatchingListService.updateByPrimaryKeySelective(petsMatchingList);
            }
        }

        petsList.setTransferUserId(userId);
        petsList.setState((byte) GlobalParams.PET_LIST_STATE_WAITING);
        petsListService.updateByPrimaryKey(petsList);
    }
    private int checkMatchingRecord(Integer userId, Integer level){
        Map<Object, Object> map = new HashMap<>();
        map.put("level", level);
        map.put("buyUserId", userId);
        map.put("state", GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
        return petsMatchingListService.selectCount(map);
    }
}
