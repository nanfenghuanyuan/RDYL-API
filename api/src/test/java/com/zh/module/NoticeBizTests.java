package com.zh.module;

import com.zh.module.biz.NoticeBiz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.entity.*;
import com.zh.module.model.PageModel;
import com.zh.module.service.*;
import com.zh.module.utils.BigDecimalUtils;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.RedisUtil;
import com.zh.module.utils.StrUtils;
import com.zh.module.variables.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class NoticeBizTests {

    @Autowired
    private NoticeBiz noticeBiz;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private RedisTemplate<String,String> redis;
    @Test
    public void appointment() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(noticeBiz.list(1, pageModel));
    }
    @Test
    public void get() {
        Integer level = 1;
        Pets pets = petsService.selectByLevel(level);
        String falseRedisKey = String.format(RedisKey.PETS_LIST_WAIT_APPOINTMENT_FALSE, level);
        Map<Object, Object> param = new HashMap<>();
        param.put("level", level);
        param.put("state", GlobalParams.PET_LIST_STATE_WAIT);
        List<PetsList> petsLists = petsListService.selectAll(param);
        for(PetsList petsList : petsLists){
            String userIds = RedisUtil.searchIndexList(redis, falseRedisKey, 0);
            if(!StrUtils.isBlank(userIds) && petsList != null) {
                Integer userId = Integer.parseInt(userIds);
                //自己不能和自己匹配
                if (petsList.getUserId() == null || petsList.getUserId().equals(userId)) {
                    RedisUtil.deleteList(redis, falseRedisKey, userId.toString());
                    continue;
                }
                param = new HashMap<>();
                param.put("buyUserId", userId);
                param.put("level", level);
                param.put("state", GlobalParams.PET_MATCHING_STATE_NOPAY);
                //查询未付款当前宠物列表
                int count = petsMatchingListService.selectCount(param);
                if (count != 0) {
                    RedisUtil.deleteList(redis, falseRedisKey, userId.toString());
                    log.info("【存在未付款当前宠物】====>" + userId);
                    continue;
                }

                Integer saleUserId = petsList.getUserId();
                //验证是否已存在预约记录
                count = checkMatchingRecord(userId, level);
                if(count == 0){
                    Account account = accountService.selectByUserIdAndAccountTypeAndType(AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, userId);
                    if(account == null || account.getAvailbalance().compareTo(pets.getPayAmount()) < 0){
                        RedisUtil.addListRight(redis, falseRedisKey, userId);
                        continue;
                    }
                }

                /*设置失效时间*/
                int interval = 10;
                Sysparams param1 = sysparamsService.getValByKey(SystemParams.PETS_MATCHING_NO_PAY_CANCEL_TIME);
                if (param1 != null) {
                    interval = Integer.parseInt(param1.getKeyval());
                }
                Calendar current = Calendar.getInstance();
                current.add(Calendar.MINUTE, interval);
                Date inactiveTime = new Timestamp(current.getTimeInMillis());

                petsList.setTransferUserId(userId);
                petsList.setState((byte) GlobalParams.PET_LIST_STATE_WAITING);
                petsListService.updateByPrimaryKey(petsList);

               try {
                    updateAccount(count, pets, userId, petsList, saleUserId, inactiveTime);
                } catch (Exception e) {
                    log.info("【扣款失败】====>" + userId);
                    RedisUtil.deleteList(redis, falseRedisKey, userId.toString());
                }
               log.info("【成功】=============>" + userId);
            }
        }
        petsLists = petsListService.selectAll(param);
        if(petsLists.size() != 0){
            get();
        }
    }
    @Test
    public void get2() throws InterruptedException {
        for(int i = 0; i < 15; i++) {
            PetsList petsList = new PetsList();
            petsList.setLevel((byte) 4);
            petsList.setPrice(new BigDecimal(1501));
            petsList.setProfitCoin("0");
            petsList.setProfitCoinRate("1");
            petsList.setPetsNumber("NO" + System.currentTimeMillis());
            petsList.setProfitDays(7);
            petsList.setProfitRate(new BigDecimal(0.21));
            petsList.setSourceFrom((byte) 1);
            petsList.setState((byte) 1);
            petsList.setStartTime(DateUtils.getCurrentTimeStr());
            petsList.setEndTime(DateUtils.getCurrentTimeStr());
            petsList.setTransferUserId(-1);
            petsList.setUserId(72);
            petsListService.insertSelective(petsList);
            Thread.sleep(100);
        }
    }
    public int checkMatchingRecord(Integer userId, Integer level){
        Map<Object, Object> map = new HashMap<>();
        map.put("level", level);
        map.put("buyUserId", userId);
        map.put("state", GlobalParams.PET_MATCHING_STATE_APPOINTMENTING);
        return petsMatchingListService.selectCount(map);
    }
    private void updateAccount(int count, Pets pets, Integer userId, PetsList petsList, Integer saleUserId, Date inactiveTime) {
        //没有预约
        if (count == 0) {
            BigDecimal appointmentAmount = pets.getPayAmount();
            accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, BigDecimalUtils.plusMinus(appointmentAmount), BigDecimal.ZERO, userId, "领养消耗", -1);

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
    }

}
