package com.zh.module;

import com.zh.module.biz.*;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SmsTemplateCode;
import com.zh.module.entity.Pets;
import com.zh.module.entity.PetsList;
import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;
import com.zh.module.service.PetsListService;
import com.zh.module.service.PetsService;
import com.zh.module.service.UsersService;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.FeigeSmsUtils;
import com.zh.module.utils.RedisUtil;
import com.zh.module.variables.RedisKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetsListBizTests {

    @Autowired
    private PetsBiz petsBiz;
    @Autowired
    private PetsMatchingListBiz petsMatchingListBiz;
    @Autowired
    private PetsListBiz petsListBiz;
    @Autowired
    private PetsV2Biz petsV2Biz;
    @Autowired
    private UsersBiz usersBiz;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private RedisTemplate<String, String> redis;

    /**
     * 自动取消预约
     */
    @Test
    public void cancelNoPaySchedule(){
        petsListBiz.cancelAppointmentSchedule();
    }

    /**
     * 自动取消未付款
     */
    @Test
    public void cancelNoConfirmSchedule(){
        petsListBiz.cancelNoPaySchedule();
    }

    /**
     * 自动确认未确认
     */
    @Test
    public void censusAppointment(){
        petsListBiz.cancelNoConfirmSchedule();
    }
    /**
     * 统计待分配宠物
     */
    @Test
    public void censusAppointment1(){
        petsListBiz.censusAppointment();
    }
    /**
     * 宠物匹配
     */
    @Test
    public void matching(){
        petsV2Biz.matching(2);
    }
}
