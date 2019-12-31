package com.zh.module;

import com.zh.module.biz.AccountBiz;
import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountBizTests {

    @Autowired
    private AccountBiz accountBiz;
    @Test
    public void init() {
        Users users = new Users();
        users.setId(1);
        System.out.println(accountBiz.init(users));
    }
    @Test
    public void personList() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(accountBiz.personProfit(users, 1, pageModel));
    }
    @Test
    public void appointment() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(accountBiz.appointmentRecord(users, pageModel));
    }
    @Test
    public void withdraw() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(accountBiz.withdraw(users, 1, "10", "940916"));
    }
    @Test
    public void recharge() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(accountBiz.recharge(users, 1, "10", "521313","940916"));
    }
    @Test
    public void withdrawList() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        Integer coinType = 1;
        System.out.println(accountBiz.withdrawList(users, coinType, pageModel));
    }
    @Test
    public void rechargeList() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        Integer coinType = 1;
        System.out.println(accountBiz.rechargeList(users, coinType, pageModel));
    }
    @Test
    public void get() {
        Users users = new Users();
        users.setId(1);
        byte accountType = 0;
        Integer coinType = 1;
        System.out.println(accountBiz.getAvailBalance(users,coinType,accountType));
    }

}
