package com.zh.module;

import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.PetsBiz;
import com.zh.module.entity.Users;
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

}
