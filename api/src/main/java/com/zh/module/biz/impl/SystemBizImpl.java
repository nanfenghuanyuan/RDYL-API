package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zh.module.biz.SystemBiz;
import com.zh.module.biz.UsersBiz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.encrypt.MD5;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.model.CoinModule;
import com.zh.module.model.Configuration;
import com.zh.module.service.*;
import com.zh.module.utils.RedisUtil;
import com.zh.module.utils.StrUtils;
import com.zh.module.utils.UUIDs;
import com.zh.module.variables.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
@Transactional
public class SystemBizImpl implements SystemBiz {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AppVersionService appVersionService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private CoinManagerService coinManagerService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String getStartupParam() {
        Notice notice = noticeService.seletByStart();
        List<CoinManager> coinManagers = coinManagerService.selectAll(new HashMap<>());
        List<CoinModule> coinModules = new LinkedList<>();
        for(CoinManager coinManager : coinManagers){
            CoinModule coinModule = new CoinModule();
            coinModule.setAddress(coinManager.getAddress());
            coinModule.setRechargeUrl(coinManager.getRechargeUrl());
            coinModule.setRechargeFee(coinManager.getRechargeFee());
            coinModule.setWithdrawFee(coinManager.getWithdrawFee());
            coinModule.setId(coinManager.getId());
            coinModule.setName(coinManager.getName());
            coinModule.setRechargeAmountMin(coinManager.getRechargeAmountMin());
            coinModule.setWithdrawAmountMin(coinManager.getWithdrawAmountMin());
            coinModule.setWithdrawDoc(coinManager.getWithdrawDoc());
            coinModule.setRechargeDoc(coinModule.getRechargeDoc());
            coinModules.add(coinModule);
        }
        String shareUrl = sysparamsService.getValStringByKey(SystemParams.REGIST_URL);
        String buyTime = sysparamsService.getValStringByKey(SystemParams.PETS_BUY_TIME);
        String commitTime = sysparamsService.getValStringByKey(SystemParams.PETS_COMMIT_TIME);
        String cartoonTime = sysparamsService.getValStringByKey(SystemParams.PETS_CARTOON_TIME);
        String commitRefreshTime = sysparamsService.getValStringByKey(SystemParams.COMMIT_REFRESH_TIME);
        Configuration config = new Configuration();
        config.setNotice(notice);
        config.setShareUrl(shareUrl + "?uuid=");
        config.setCoinList(coinModules);
        config.setBuyTime(buyTime);
        config.setCartoonTime(cartoonTime);
        config.setCommitTime(commitTime);
        config.setCommitRefreshTime(commitRefreshTime);
        return Result.toResult(ResultCode.SUCCESS, config);
    }

    @Override
    public String getCustomerService() {
        String wechatAccount = sysparamsService.getValStringByKey(SystemParams.WECHAT_ACCOUNT);
        String wechatImgUrl = sysparamsService.getValStringByKey(SystemParams.WECHAT_IMG_URL);
        Map<String, Object> map = new HashMap<>();
        map.put("account", wechatAccount);
        map.put("imgUrl", wechatImgUrl);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String checkUpdate(Integer version) {
        Map<String, Object> map = new HashMap<String, Object>();
        /*版本更新信息*/
        List<AppVersion> vers = appVersionService.getByVersion(version);
        if(vers == null || vers.isEmpty()){
            map.put("updateFlag", false);
        }else{
            AppVersion listVersion = vers.get(0);
            map.put("updateFlag", true);
            map.put("updateVersion", listVersion.getAppVersion());
            map.put("url", listVersion.getAddress());
            map.put("content", listVersion.getContent());
        }
        return Result.toResult(ResultCode.SUCCESS, map);
    }
}
