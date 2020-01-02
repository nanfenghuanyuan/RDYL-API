package com.zh.module;

import com.zh.module.biz.HomeBiz;
import com.zh.module.biz.SmsCodeBiz;
import com.zh.module.entity.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsBizTests {

    @Autowired
    private SmsCodeBiz smsCodeBiz;
    @Test
    public void init() {
        System.out.println(smsCodeBiz.getValidateCode("13165373280", 1));
    }

}
