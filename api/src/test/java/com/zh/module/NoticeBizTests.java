package com.zh.module;

import com.zh.module.biz.AccountBiz;
import com.zh.module.biz.NoticeBiz;
import com.zh.module.entity.Pets;
import com.zh.module.entity.PetsList;
import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;
import com.zh.module.service.PetsListService;
import com.zh.module.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeBizTests {

    @Autowired
    private NoticeBiz noticeBiz;
    @Autowired
    private PetsListService petsListService;
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
    @Test
    public void get2() throws InterruptedException {
        for(int i = 0; i < 4; i++) {
            PetsList petsList = new PetsList();
            petsList.setLevel((byte) 2);
            petsList.setPrice(new BigDecimal(301));
            petsList.setProfitCoin("0");
            petsList.setProfitCoinRate("1");
            petsList.setPetsNumber("NO" + System.currentTimeMillis());
            petsList.setProfitDays(7);
            petsList.setProfitRate(new BigDecimal(0.21));
            petsList.setSourceFrom((byte) 1);
            petsList.setState((byte) 1);
            petsList.setStartTime(DateUtils.getCurrentTimeStr());
            petsList.setEndTime(DateUtils.getCurrentTimeStr());
            petsList.setTransferUserId(-1);
            petsList.setUserId(72);
            petsListService.insertSelective(petsList);
            Thread.sleep(100);
        }
    }

}
