package com.zh.module;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.biz.AccountBiz;
import com.zh.module.biz.UsersBiz;
import com.zh.module.entity.BindInfo;
import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;
import com.zh.module.model.PayInfoModel;
import com.zh.module.service.BindInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountBizTests {

    @Autowired
    private AccountBiz accountBiz;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private UsersBiz usersBiz;
    @Test
    public void init() {
        Users users = new Users();
        users.setId(1);
        System.out.println(accountBiz.init(users));
    }
    @Test
    public void transfer() throws Exception {
        Users users = usersBiz.getUser(1);
        System.out.println(accountBiz.transfer(users, "13165555555", "10", "123456"));
    }
    @Test
    public void personList() {
        Users users = usersBiz.getUser(16);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(accountBiz.personProfit(users, 0, pageModel));
    }
    @Test
    public void appointment() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(accountBiz.appointmentRecord(users, pageModel));
    }
    @Test
    public void withdraw() throws Exception {
        Users users = usersBiz.getUser(25);
        System.out.println(accountBiz.withdraw(users, 1, "500", "123456"));
    }
    @Test
    public void recharge() throws Exception {
        Users users = new Users();
        users.setId(1);
        users.setOrderPwd("e10adc3949ba59abbe56e057f20f883e");
        PageModel pageModel = new PageModel(1, 10);
        System.out.println(accountBiz.recharge(users, 1, "10", "521313","123456"));
    }
    @Test
    public void withdrawList() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        Integer coinType = 1;
        System.out.println(accountBiz.withdrawList(users, coinType, pageModel));
    }
    @Test
    public void rechargeList() {
        Users users = new Users();
        users.setId(1);
        PageModel pageModel = new PageModel(1, 10);
        Integer coinType = 1;
        System.out.println(accountBiz.rechargeList(users, coinType, pageModel));
    }
    @Test
    public void get() {
        Users users = usersBiz.getUser(80);
        byte accountType = 0;
        Integer coinType = 1;
        System.out.println(accountBiz.getAvailBalance(users,coinType,accountType));
    }
    @Test
    public void get1() {
        Users users = usersBiz.getUser(4);
        byte accountType = 0;
        Integer coinType = 0;
        System.out.println(accountBiz.getWithdrawBalance(users,coinType,accountType));
    }
    @Test
    public void get2() {
        List<BindInfo> bindInfos = bindInfoService.queryByUser(23);
        List<PayInfoModel> payInfoModels = new ArrayList<>();
        for(BindInfo bindInfo : bindInfos){
            PayInfoModel payInfoModel = new PayInfoModel();
            payInfoModel.setAccount(bindInfo.getAccount());
            payInfoModel.setImgUrl(bindInfo.getImgUrl());
            payInfoModel.setName(bindInfo.getName());
            payInfoModel.setType(bindInfo.getType().intValue());
            payInfoModel.setBankName(bindInfo.getBankName());
            payInfoModel.setBranchName(bindInfo.getBranchName());
            payInfoModels.add(payInfoModel);
        }
        System.out.println(JSONObject.toJSONString(payInfoModels));
        payInfoModels = payInfoModels.stream().sorted(Comparator.comparing(PayInfoModel::getType)).collect(Collectors.toList());
        System.out.println(JSONObject.toJSONString(payInfoModels));
    }

}
