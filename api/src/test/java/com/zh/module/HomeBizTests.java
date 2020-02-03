package com.zh.module;

import com.zh.module.biz.BindInfoBiz;
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
public class HomeBizTests {

    @Autowired
    private HomeBiz homeBiz;

    @Autowired
    private UsersBiz usersBiz;

    @Autowired
    private BindInfoBiz bindInfoBiz;
    @Test
    public void init() {
        Users users = new Users();
        users.setId(1);
        System.out.println(homeBiz.init(users));
    }
    @Test
    public void bind(){
        Users users = usersBiz.getUser(43);
        System.out.println(bindInfoBiz.get(users, 0));
    }

}
