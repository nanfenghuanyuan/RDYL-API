package com.zh.module;

import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.UsersBiz;
import com.zh.module.entity.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBizTests {

    @Autowired
    private UsersBiz usersBiz;
    @Test
    public void init() throws Exception {
        Users users = new Users();
        users.setId(1);
        users.setPassword("qq940916");
        users.setPhone("13165373280");
        System.out.println(usersBiz.login(users));
    }

}
