package com.zh.module;

import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.SystemBiz;
import com.zh.module.entity.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-25 12:06
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemBizTest {
    @Autowired
    private SystemBiz systemBiz;
    @Test
    public void init() {
        System.out.println(systemBiz.getStartupParam());
    }
    @Test
    public void custom() {
        System.out.println(systemBiz.getCustomerService());
    }
    @Test
    public void checkUpdate() {
        System.out.println(systemBiz.checkUpdate(1));
    }
}
