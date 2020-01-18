package com.zh.module;

import com.zh.module.aliyun.H5RpBasic;
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
    @Autowired
    private H5RpBasic h5RpBasic;
    @Test
    public void init() throws Exception {
        Users users = new Users();
        users.setId(1);
        users.setPassword("qq940916");
        users.setPhone("13165373280");
        System.out.println(usersBiz.login(users));
    }
    @Test
    public void realName() throws Exception {
        System.out.println(h5RpBasic.init("唐立慧", "370883199403287261"));
    }
    @Test
    public void getAuthState() throws Exception {
        Users users = usersBiz.getUser(1);
        System.out.println(usersBiz.getAuthState(users));
    }
    @Test
    public void regiester() throws Exception {
        System.out.println(usersBiz.register("13666666667", "123456", "19216441", 1, "303894"));
    }
    @Test
    public void login() throws Exception {
        Users users = new Users();
        users.setPhone("13666666667");
        users.setPassword("123456");
        System.out.println(usersBiz.login(users));
    }
    @Test
    public void realName1() throws Exception {
        Users users = usersBiz.getUser(1);
        System.out.println(usersBiz.getToken(users, "赵赫", "370883199409167412"));
    }
    @Test
    public void realName2() throws Exception {
        Users users = usersBiz.getUser(1);
        System.out.println(usersBiz.getStatus(1));
    }

}
