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
    public void realName1() throws Exception {
        System.out.println(h5RpBasic.getStatus("f6f833fd-aff6-47e4-ad0f-b4f019bad817"));
    }

}
