package com.zh.module;

import com.zh.module.biz.*;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SmsTemplateCode;
import com.zh.module.entity.Pets;
import com.zh.module.entity.PetsList;
import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;
import com.zh.module.service.*;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.FeigeSmsUtils;
import com.zh.module.utils.RedisUtil;
import com.zh.module.variables.RedisKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetsBizTests {

    @Autowired
    private PetsBiz petsBiz;
    @Autowired
    private PetsV2Biz petsV2Biz;
    @Autowired
    private PetsMatchingListBiz petsMatchingListBiz;
    @Autowired
    private PetsListBiz petsListBiz;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersBiz usersBiz;
    @Autowired
    private PetsService petsService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private RedisTemplate<String, String> redis;

    @Test
    public void buy() throws ParseException {
        Users users = usersBiz.getUser(26);
        System.out.println(petsBiz.buy(users, 4));
    };
    @Test
    public void init() throws ParseException {
        Users users = new Users();
        users.setId(1);
        System.out.println(petsBiz.appointment(users, 2));
    }
    @Test
    public void matchingList() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(petsMatchingListBiz.list(users, 1, pageModel));
    }
    @Test
    public void list() {
        Users users = usersBiz.getUser(40);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(petsListBiz.list(users, 3, pageModel));
    }
    @Test
    public void petList() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(petsBiz.list(users, pageModel));
    }

    @Test
    public void get() {
        Users users = new Users();
        users.setId(1);
        System.out.println(petsListBiz.get(users, 1));
    }
    @Test
    public void getBuyState() {
        Users users = usersBiz.getUser(1368);
        System.out.println(petsBiz.getBuyState(users, 2));
    }
    @Test
    public void confirmPay() throws Exception {
        Users users = usersService.selectByPrimaryKey(1);
        System.out.println(petsListBiz.confirmPay(users, 1, "123456", "1"));
    }
    @Test
    public void confirmReceipt() throws Exception {
        Users users = usersService.selectByPrimaryKey(1);
        System.out.println(petsListBiz.confirmReceipt(users, 1, "123456"));
    }
    @Test
    public void getProfit(){
        petsListBiz.getProfit();
    }
    @Test
    public void confirmReceipt1() {
        FeigeSmsUtils feigeSmsUtils = new FeigeSmsUtils();
        feigeSmsUtils.sendTemplatesSms("13165373280", SmsTemplateCode.SMS_C2C_PAY_NOTICE, "");
    }
    @Test
    public void get1() {
        Users users = usersBiz.getUser(72);
        System.out.println(petsV2Biz.get(users, 4));
    }
    @Test
    public void get22() {
        petsV2Biz.matching(4);

    }

    @Test
    public void confirmReceipt2() {
        Map<Object, Object> param = new HashMap<>();
        param.put("state", GlobalParams.ACTIVE);
        param.put("level", 4);
        List<Pets> petsList = petsService.selectAll(param);
        String time;
        String endTime;
        String redisKey;
        List<PetsList> petsLists;
        List<PetsList> disList = new LinkedList<>();
        for(Pets pets : petsList){
            endTime = DateUtils.getCurrentDateStr() + " " + pets.getEndTime() + ":00";
                param = new HashMap<>();
                param.put("level", pets.getLevel());
                param.put("state", GlobalParams.PET_LIST_STATE_WAIT);
                petsLists = petsListService.selectAll(param);
                for (PetsList petsList1 : petsLists) {
                    disList.add(petsList1);
                    redisKey = String.format(RedisKey.PETS_LIST_WAIT_APPOINTMENT, pets.getLevel());
                    if (petsLists.size() != 0) {
                        RedisUtil.deleteKey(redis, redisKey);
                    }

                    RedisUtil.addListRight(redis, redisKey, disList);
                }
                System.out.println(pets.getName() + "==本次参与分配的宠物有" + petsLists.size() + "个");
                redisKey = String.format(RedisKey.PETS_LIST_WAIT_APPOINTMENT_AMOUNT, pets.getLevel());
                RedisUtil.addString(redis, redisKey, String.valueOf(petsLists.size()));
        }
    }

    @Test
    public void petsToMepc() {
        Map<Object, Object> param = new HashMap<>();
        param.put("state", GlobalParams.PET_LIST_STATE_WAIT);
        List<PetsList> petsLists = petsListService.selectAll(param);
        for(PetsList petsList : petsLists){
            BigDecimal amount = petsList.getPrice().multiply(new BigDecimal(2));
            //删除该宠物的所有转让记录
            petsMatchingListService.deleteAllByPetListId(petsList.getId());
            //删除该宠物
            petsListService.deleteByPrimaryKey(petsList.getId());
            accountService.updateAccountAndInsertFlow(petsList.getUserId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, amount, BigDecimal.ZERO, petsList.getUserId(), "MEPC兑换", petsList.getId());
        }
    }
    @Test
    public void petsToMepc1() {
        PetsList petsList = petsListService.selectByPrimaryKey(79);
        BigDecimal amount = petsList.getPrice().multiply(new BigDecimal(2));
        //删除该宠物的所有转让记录
        petsMatchingListService.deleteAllByPetListId(petsList.getId());
        //删除该宠物
        petsListService.deleteByPrimaryKey(petsList.getId());
        accountService.updateAccountAndInsertFlow(petsList.getUserId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, amount, BigDecimal.ZERO, petsList.getUserId(), "MEPC兑换", petsList.getId());
        }
}
