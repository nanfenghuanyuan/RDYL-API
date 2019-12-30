package com.zh.module;

import com.zh.module.biz.AccountBiz;
import com.zh.module.biz.NoticeBiz;
import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeBizTests {

    @Autowired
    private NoticeBiz noticeBiz;
    @Test
    public void appointment() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(noticeBiz.list(1, pageModel));
    }
    @Test
    public void get() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(noticeBiz.get(6));
    }

}
