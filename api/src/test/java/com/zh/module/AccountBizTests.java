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

}
