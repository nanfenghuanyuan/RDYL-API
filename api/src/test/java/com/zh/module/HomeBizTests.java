package com.zh.module;

import com.zh.module.biz.HomeBiz;
import com.zh.module.entity.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeBizTests {

    @Autowired
    private HomeBiz homeBiz;
    @Test
    public void init() {
        Users users = new Users();
        users.setId(1);
        System.out.println(homeBiz.init(users));
    }

}