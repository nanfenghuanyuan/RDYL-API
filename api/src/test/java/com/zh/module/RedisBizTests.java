package com.zh.module;

import com.zh.module.biz.NoticeBiz;
import com.zh.module.entity.Notice;
import com.zh.module.entity.PetsList;
import com.zh.module.entity.Users;
import com.zh.module.model.PageModel;
import com.zh.module.service.PetsListService;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.RedisUtil;
import com.zh.module.variables.RedisKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisBizTests {

    @Autowired
    private NoticeBiz noticeBiz;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PetsListService petsListService;
    @Test
    public void appointment() {
        List<Notice> list = new LinkedList<>();
       for(int i = 0; i < 101; i ++){
           Notice notice = new Notice();
           notice.setId(i);
           list.add(notice);
       }
        RedisUtil.addListRight(redisTemplate, "test", list);
    }
    @Test
    public void appointment1() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("s", "@");
        String redisKey = String.format(RedisKey.TEAM_INFO, 1, 1);
        Map<String, Object>  s = RedisUtil.searchStringObj(redisTemplate, redisKey, Map.class);
        System.out.println(s.toString());
    }
}
