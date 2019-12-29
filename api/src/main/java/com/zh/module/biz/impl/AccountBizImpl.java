package com.zh.module.biz.impl;

import com.zh.module.biz.AccountBiz;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.exception.BanlanceNotEnoughException;
import com.zh.module.model.FlowModel;
import com.zh.module.service.*;
import com.zh.module.utils.BigDecimalUtils;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AccountBizImpl extends BaseBizImpl implements AccountBiz {
    @Autowired
    private AccountService accountService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String init(Users users) {
        Map<String, Object> map = new HashMap<>();
        Map<Object, Object> params = new HashMap<>();
        Account account = accountService.selectByUserIdAndAccountTypeAndType(AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, users.getId());
        map.put("amount", account.getAvailbalance().stripTrailingZeros().toPlainString());
        params.put("userId", users.getId());
        params.put("coinType", CoinType.OS);
        params.put("accountType", AccountType.ACCOUNT_TYPE_ACTIVE);
        List<Flow> flows = flowService.selectAll(params);
        List<FlowModel> flowModels = new LinkedList<>();
        for(Flow flow : flows){
            FlowModel flowModel = new FlowModel();
            flowModel.setAmount(flow.getAmount());
            flowModel.setOperaType(flow.getOperType());
            flowModel.setTime(DateUtils.getDateFormate(flow.getCreateTime()));
            flowModels.add(flowModel);
        }
        map.put("flows", flowModels);
        String doc = sysparamsService.getValStringByKey(SystemParams.GOLD_HELP_DOC);
        map.put("explain", doc);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String transfer(Users users, String phone, String amount, String password) {
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(users, password);
            if(valiStr != null){
                return valiStr;
            }
        }
        String transferRole = sysparamsService.getValStringByKey(SystemParams.TRANSFER_ROLE);
        Users toUser = usersService.selectByPhone(phone);
        //检查是否仅上下级进行转账
        if(!StrUtils.isBlank(transferRole)){
            if(toUser == null){
                return Result.toResult(ResultCode.USER_NOT_EXIST);
            }
            String uuid = toUser.getUuid();
            String uuid2 = users.getUuid();
            if(!uuid.equals(users.getReferId()) || !uuid2.equals(toUser.getReferId())){
                return Result.toResult(ResultCode.TRANS_ROLE);
            }
        }
        try {
            accountService.updateAccountAndInsertFlow(toUser.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, new BigDecimal(amount), BigDecimal.ZERO, users.getId(), "转入(" + users.getPhone()+")", 1);
            accountService.updateAccountAndInsertFlow(users.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, BigDecimalUtils.plusMinus(new BigDecimal(amount)), BigDecimal.ZERO, users.getId(), "转出(" + phone+")", 1);
        } catch (BanlanceNotEnoughException e) {
            log.info("资金划转失败");
            e.printStackTrace();
            throw new RuntimeException();
        }
        //下级激活账户
        if(toUser.getReferId().equals(users.getUuid()) && toUser.getState() == GlobalParams.INACTIVE){
            toUser.setState((byte) GlobalParams.ACTIVE);
            usersService.updateByPrimaryKeySelective(toUser);
        }

        return Result.toResult(ResultCode.SUCCESS);
    }
}