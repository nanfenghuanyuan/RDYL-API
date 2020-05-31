package com.zh.module;

import com.zh.module.biz.TeamBiz;
import com.zh.module.biz.UsersBiz;
import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamBizTests {

    @Autowired
    private TeamBiz teamBiz;
    @Autowired
    private UsersBiz usersBiz;
    @Test
    public void init() {
        Users users = usersBiz.getUser(44);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(teamBiz.init(users, 2, pageModel));
    }

    @Test
    public void getProfit() {
        teamBiz.getProfit();
    }
    @Test
    public void dayProfit() {
        teamBiz.dayProfit();
    }

}
