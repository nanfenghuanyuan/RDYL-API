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
import com.zh.module.model.*;
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
import java.util.*;

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
    private ProfitRecordService profitRecordService;
    @Autowired
    private AppointmentRecordService appointmentRecordService;
    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private AccountTransferService accountTransferService;
    @Autowired
    private PetsListService petsListService;
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
            flowModel.setAmount(flow.getAmount().stripTrailingZeros().toPlainString());
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
            Set<Integer> userSet = new HashSet<>();
            getReferSetUp(users.getUuid(), userSet);
            getReferSetloss(users.getUuid(), userSet);
            if(!userSet.contains(toUser.getId())){
                return Result.toResult(ResultCode.TRANS_ROLE);
            }
        }

        if(new BigDecimal(amount).compareTo(new BigDecimal("99999999")) > 0){
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }

        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setAccountType((byte) AccountType.ACCOUNT_TYPE_ACTIVE);
        accountTransfer.setAmount(new BigDecimal(amount));
        accountTransfer.setCoinType((byte) CoinType.OS);
        accountTransfer.setRelatedid(-1);
        accountTransfer.setToUserId(toUser.getId());
        accountTransfer.setUserId(users.getId());
        accountTransferService.insertSelective(accountTransfer);

        try {
            accountService.updateAccountAndInsertFlow(toUser.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, new BigDecimal(amount), BigDecimal.ZERO, users.getId(), "转入(" + users.getPhone()+")", 1);
            accountService.updateAccountAndInsertFlow(users.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, BigDecimalUtils.plusMinus(new BigDecimal(amount)), BigDecimal.ZERO, users.getId(), "转出(" + phone+")", 1);
        } catch (BanlanceNotEnoughException e) {
            log.info("资金划转失败");
            e.printStackTrace();
            throw new BanlanceNotEnoughException("账户余额不足");
        }
        //下级激活账户
        if(toUser.getReferId().equals(users.getUuid()) && toUser.getState() == GlobalParams.INACTIVE){
            toUser.setState((byte) GlobalParams.ACTIVE);
            usersService.updateByPrimaryKeySelective(toUser);
        }

        return Result.toResult(ResultCode.SUCCESS);
    }

    /**
     * 向下
     */
    private Set<Integer> getReferSetloss(String referId, Set<Integer> userSet) {
        Users user = usersService.selectByReferID(referId);
        if(user != null){
            userSet.add(user.getId());
            getReferSetloss(user.getUuid(), userSet);
        }
        return userSet;
    }

    /**
     * 向上
     */
    private Set<Integer> getReferSetUp(String uuid, Set<Integer> userSet) {
        Users user = usersService.selectByUUID(uuid);
        if(user != null){
            userSet.add(user.getId());
            getReferSetUp(user.getReferId(), userSet);
        }
        return userSet;
    }

    @Override
    public String personProfit(Users users, Integer type, PageModel pageModel) {
        Integer userId = users.getId();
        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> result = new HashMap<>();
        String sumAmount;
        List<FlowModel> flowModels = new LinkedList<>();
        if(type == 0){
            map.put("userId", userId);
            map.put("operType", "个人收益");
            map.put("firstResult", pageModel.getFirstResult());
            map.put("maxResult", pageModel.getMaxResult());
            List<Flow> flows = flowService.selectPaging(map);
            for(Flow flow : flows){
                FlowModel flowModel = new FlowModel();
                flowModel.setTime(DateUtils.getDateFormate(flow.getCreateTime()));
                flowModel.setOperaType(flow.getOperType());
                flowModel.setAmount(flow.getAmount().compareTo(BigDecimal.ZERO) > 0 ? "+" + flow.getAmount().toPlainString() : "-" + flow.getAmount().toPlainString());
                flowModels.add(flowModel);
            }
            sumAmount = flowService.selectPersonProfitSumAmount(userId, "个人收益");
        }else {
            map.put("userId", userId);
            map.put("type", type);
            map.put("firstResult", pageModel.getFirstResult());
            map.put("maxResult", pageModel.getMaxResult());
            List<ProfitRecord> profitRecords = profitRecordService.selectPaging(map);
            for (ProfitRecord profitRecord : profitRecords) {
                FlowModel flowModel = new FlowModel();
                flowModel.setTime(DateUtils.getDateFormate(profitRecord.getCreateTime()));
                flowModel.setOperaType(profitRecord.getRemark());
                flowModel.setAmount(profitRecord.getAmount().compareTo(BigDecimal.ZERO) > 0 ? "+" + profitRecord.getAmount().toPlainString() : "-" + profitRecord.getAmount().toPlainString());
                flowModels.add(flowModel);
            }
            sumAmount = profitRecordService.selectSumAmount(userId, type);
        }
        result.put("sumAmount", sumAmount);
        result.put("list", flowModels);
        return Result.toResult(ResultCode.SUCCESS, result);
    }

    @Override
    public String appointmentRecord(Users users, PageModel pageModel) {
        Integer userId = users.getId();
        Map<Object, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("firstResult", pageModel.getFirstResult());
        map.put("maxResult", pageModel.getMaxResult());
        List<AppointmentRecord> appointmentRecords = appointmentRecordService.selectPaging(map);
        List<AppoinModel> flowModels = new LinkedList<>();
        String amount = "";
        for(AppointmentRecord appointmentRecord : appointmentRecords){
            AppoinModel appoinModel = new AppoinModel();
            appoinModel.setTime(DateUtils.getDateFormate(appointmentRecord.getCreateTime()));
            appoinModel.setName(appointmentRecord.getName());
            if(appointmentRecord.getName().contains("返还")){
                amount = " + " + appointmentRecord.getSpend();
            }else{
                amount = " - " + appointmentRecord.getSpend();
            }
            appoinModel.setAmount(amount);
            flowModels.add(appoinModel);
        }
        return Result.toResult(ResultCode.SUCCESS, flowModels);
    }

    @Override
    public String withdraw(Users users, Integer coinType, String amount, String password){
        Integer userId = users.getId();
        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(users, password);
            if(valiStr!=null){
                return valiStr;
            }
        }
        BigDecimal amountBig = new BigDecimal(amount);
        Account account = accountService.selectByUserIdAndAccountTypeAndType(AccountType.ACCOUNT_TYPE_ACTIVE, coinType, userId);
        if(amountBig.compareTo(account.getAvailbalance()) > 0){
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }

        //保存记录
        Withdraw withdraw = new Withdraw();
        withdraw.setAmount(amountBig);
        withdraw.setCoinType(coinType.byteValue());
        withdraw.setState((byte) GlobalParams.WITHDRAW_NO_PAY);
        withdraw.setUserId(userId);
        withdrawService.insertSelective(withdraw);

        accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_TYPE_ACTIVE, coinType, BigDecimalUtils.plusMinus(amountBig), BigDecimal.ZERO, userId, "提现发起", withdraw.getId());

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String getAvailBalance(Users users, Integer coinType, byte accountType) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = users.getId();
        Account account = accountService.selectByUserIdAndAccountTypeAndType(accountType, coinType, userId);
        result.put("contribution", users.getContribution());
        result.put("availbalance", account.getAvailbalance());
        result.put("teamLevel", users.getTeamLevel());
        String holdAssets = petsListService.selectSumAmountByUser(userId);
        holdAssets = StrUtils.isBlank(holdAssets) ? "0" : holdAssets;
        result.put("holdAssets", new BigDecimal(holdAssets).setScale(0, BigDecimal.ROUND_HALF_UP));
        return Result.toResult(ResultCode.SUCCESS, result);
    }

    @Override
    public String withdrawList(Users users, Integer coinType, PageModel pageModel) {
        Map<Object, Object> map = new HashMap<>();
        map.put("userId", users.getId());
        map.put("coinType", coinType);
        map.put("firstResult", pageModel.getFirstResult());
        map.put("maxResult", pageModel.getMaxResult());
        List<Withdraw> withdraws = withdrawService.selectPaging(map);
        List<WithdrawModel> list = new LinkedList<>();
        for(Withdraw withdraw : withdraws){
            WithdrawModel withdrawModel = new WithdrawModel();
            withdrawModel.setAmount(withdraw.getAmount());
            withdrawModel.setState(WithdrawModel.getStates(withdraw.getState().intValue()));
            withdrawModel.setTime(DateUtils.getDateFormate(withdraw.getCreateTime()));
            list.add(withdrawModel);
        }
        return Result.toResult(ResultCode.SUCCESS, list);
    }

    @Override
    public String recharge(Users users, Integer coinType, String amount, String address, String password) {
        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(users, password);
            if(valiStr!=null){
                return valiStr;
            }
        }
        BigDecimal amountBig = new BigDecimal(amount);
        Recharge recharge = new Recharge();
        recharge.setAmount(amountBig);
        recharge.setCoinType(coinType.byteValue());
        recharge.setState((byte) GlobalParams.RECHARGE_STATE_NEW);
        recharge.setUserId(users.getId());
        recharge.setAddress(address);
        rechargeService.insertSelective(recharge);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String rechargeList(Users users, Integer coinType, PageModel pageModel) {
        Map<Object, Object> map = new HashMap<>();
        map.put("userId", users.getId());
        map.put("coinType", coinType);
        map.put("firstResult", pageModel.getFirstResult());
        map.put("maxResult", pageModel.getMaxResult());
        List<Recharge> recharges = rechargeService.selectPaging(map);
        List<RechargeModel> list = new LinkedList<>();
        for(Recharge recharge : recharges){
            RechargeModel rechargeModel = new RechargeModel();
            rechargeModel.setAmount(recharge.getAmount());
            rechargeModel.setState(rechargeModel.getStates(recharge.getState().intValue()));
            rechargeModel.setCoinType(CoinType.getCoinName(recharge.getCoinType().intValue()));
            rechargeModel.setTime(DateUtils.getDateFormate(recharge.getCreateTime()));
            list.add(rechargeModel);
        }
        return Result.toResult(ResultCode.SUCCESS, list);
    }

    @Override
    public String getWithdrawBalance(Users users, Integer coinType, byte accountType) {
        Integer userId = users.getId();
        Account account = accountService.selectByUserIdAndAccountTypeAndType(accountType, coinType, userId);
        return Result.toResult(ResultCode.SUCCESS, account.getAvailbalance().stripTrailingZeros().toPlainString());
    }
}