package com.zh.module;

import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.PetsBiz;
import com.zh.module.biz.PetsListBiz;
import com.zh.module.biz.PetsMatchingListBiz;
import com.zh.module.constants.SmsTemplateCode;
import com.zh.module.entity.PetsMatchingList;
import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;
import com.zh.module.service.UsersService;
import com.zh.module.utils.FeigeSmsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetsBizTests {

    @Autowired
    private PetsBiz petsBiz;
    @Autowired
    private PetsMatchingListBiz petsMatchingListBiz;
    @Autowired
    private PetsListBiz petsListBiz;
    @Autowired
    private UsersService usersService;
    @Test
    public void buy() {
        Users users = new Users();
        users.setId(1);
        System.out.println(petsBiz.buy(users, 2));
    };
    @Test
    public void init() {
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
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(petsListBiz.list(users, 2, pageModel));
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
    public void confirmPay() {
        Users users = usersService.selectByPrimaryKey(1);
        System.out.println(petsListBiz.confirmPay(users, 1, "123456"));
    }
    @Test
    public void confirmReceipt() {
        Users users = usersService.selectByPrimaryKey(1);
        System.out.println(petsListBiz.confirmReceipt(users, 1, "123456"));
    }
    @Test
    public void confirmReceipt1() {
        FeigeSmsUtils feigeSmsUtils = new FeigeSmsUtils();
        feigeSmsUtils.sendTemplatesSms("13165373280", SmsTemplateCode.SMS_C2C_PAY_NOTICE, "");
    }

}
