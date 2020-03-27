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
    private PetsService petsService;
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private WithdrawQuoteService withdrawQuoteService;
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
    public String transfer(Users users, String phone, String amount, String password) throws Exception {
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        if(phone.equals(users.getPhone())){
            return Result.toResult(ResultCode.TRANS_ROLE);
        }
        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(users, password);
            if(valiStr != null){
                return valiStr;
            }
        }
        //最小转增金额
        String transferMinAmount = sysparamsService.getValStringByKey(SystemParams.TRANSFER_MIN_AMOUNT);
        String transferMinAmount_remain = sysparamsService.getValStringByKey(SystemParams.TRANSFER_MIN_AMOUNT_REMAIN);
        if(new BigDecimal(amount).compareTo(new BigDecimal(transferMinAmount)) < 0){
            return Result.toResultFormat(ResultCode.TRANSFER_MIN_BALANCE, transferMinAmount_remain);
        }
        Account accounts = accountService.selectByUserIdAndAccountTypeAndType(AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, users.getId());
        if(accounts != null && accounts.getAvailbalance().subtract(new BigDecimal(amount)).compareTo(new BigDecimal(transferMinAmount_remain)) < 0){
            return Result.toResultFormat(ResultCode.TRANSFER_MIN_AMOUNT, transferMinAmount_remain);
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

        //下级激活账户
        if(toUser.getState() == GlobalParams.INACTIVE){
            String transferAmount = sysparamsService.getValStringByKey(SystemParams.TRANSFER_AMOUNT_ACTIVE);
            String account = accountService.queryByUserIdAndAccountTypeAndType(toUser.getId());
            BigDecimal balance = new BigDecimal(account).add(new BigDecimal(amount));
            if(balance.compareTo(new BigDecimal(transferAmount)) >= 0) {
                toUser.setState((byte) GlobalParams.ACTIVE);
                usersService.updateByPrimaryKeySelective(toUser);
            }
        }

        try {
            accountService.updateAccountAndInsertFlow(toUser.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, new BigDecimal(amount), BigDecimal.ZERO, users.getId(), "转入(" + users.getPhone()+")", 1);
            accountService.updateAccountAndInsertFlow(users.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, BigDecimalUtils.plusMinus(new BigDecimal(amount)), BigDecimal.ZERO, users.getId(), "转出(" + phone+")", 1);
        } catch (BanlanceNotEnoughException e) {
            log.info("资金划转失败");
            e.printStackTrace();
            throw new BanlanceNotEnoughException("账户余额不足");
        }


        return Result.toResult(ResultCode.SUCCESS);
    }

    /**
     * 向下
     */
    private Set<Integer> getReferSetloss(String referId, Set<Integer> userSet) {
        List<Users> user = usersService.selectByReferID(referId);
        for(Users users : user) {
            if (users != null) {
                userSet.add(users.getId());
                getReferSetloss(users.getUuid(), userSet);
            }
        }
        return userSet;
    }

    /**
     * 向上
     */
    private Set<Integer> getReferSetUp(String uuid, Set<Integer> userSet) {
        List<Users> user = usersService.selectByUUIDList(uuid);
        for(Users users : user) {
            if (users != null) {
                userSet.add(users.getId());
                getReferSetUp(users.getReferId(), userSet);
            }
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
    public String withdraw(Users users, Integer coinType, String amount, String password) throws Exception {
        //校验提现开关
        String onoff = sysparamsService.getValStringByKey(SystemParams.WITHDRAW_ONOFF);
        if(Integer.parseInt(onoff) == GlobalParams.INACTIVE){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        //提现开始时间
        String start = sysparamsService.getValStringByKey(SystemParams.WITHDRAW_TIME_LIMIT_START);
        start = DateUtils.getCurrentDateStr() + " " + start;
        //提现结束时间
        String end = sysparamsService.getValStringByKey(SystemParams.WITHDRAW_TIME_LIMIT_END);
        end = DateUtils.getCurrentDateStr() + " " + end;
        if(DateUtils.minBetween(start) >= 0 && DateUtils.minBetween(end) < 0) {
            //检查金额是否正确
            String result = checkAmount(amount, users);
            if(result != null){
                return result;
            }
            Integer userId = users.getId();
            /*校验提现额度*/
            WithdrawQuote withdrawQuote = withdrawQuoteService.selectByUser(userId);
            if(withdrawQuote == null){
                return Result.toResult(ResultCode.WITHDRAW_QUOTA_NONE);
            }
            if(new BigDecimal(amount).compareTo(withdrawQuote.getAmount()) > 0){
                return Result.toResult(ResultCode.WITHDRAW_QUOTA_NONE);
            }else{
                withdrawQuote.setAmount(withdrawQuote.getAmount().subtract(new BigDecimal(amount)));
                withdrawQuoteService.updateByPrimaryKeySelective(withdrawQuote);

                Flow flow = new Flow();
                flow.setAccountType(AccountType.ACCOUNT_TYPE_ACTIVE);
                flow.setAmount(new BigDecimal(amount));
                flow.setCoinType(coinType);
                flow.setOperId(userId);
                flow.setOperType("提现消耗额度");
                flow.setRelateId(withdrawQuote.getId());
                flow.setResultAmount(withdrawQuote.getAmount().toPlainString());
                flow.setUserId(userId);
                flowService.insertSelective(flow);
            }
            //获取等级对应日最多提现金额
            String amountLevel = sysparamsService.getValStringByKey(Users.getWithdrawLevel(users.getTeamLevel().intValue()));
            amountLevel = StrUtils.isBlank(amountLevel) ? "0" : amountLevel;
            String today = DateUtils.getCurrentDateStr();
            //今日提现总金额
            String dayAmount = withdrawService.totalDayAmount(userId, coinType, today);
            dayAmount = StrUtils.isBlank(dayAmount) ? "0" : dayAmount;
            //若限额小于日提现金额
            if(new BigDecimal(amountLevel).compareTo(new BigDecimal(dayAmount)) < 0){
                return Result.toResult(ResultCode.WITHDRAW_SUM_LIMIT);
            }
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

            //自动生成宠物开关
            String autoWithdraw = sysparamsService.getValStringByKey(SystemParams.AUTO_WITHDRAW);
            if(!StrUtils.isBlank(autoWithdraw) && "1".equals(autoWithdraw)){
                Pets pets = petsService.selectByPrice(amount);
                if(pets != null) {
                    PetsList petsList = new PetsList();
                    petsList.setLevel(pets.getLevel());
                    petsList.setPrice(amountBig);
                    petsList.setProfitCoin("0");
                    petsList.setProfitCoinRate("1");
                    petsList.setPetsNumber("NO" + System.currentTimeMillis());
                    petsList.setProfitDays(pets.getProfitDays());
                    petsList.setProfitRate(pets.getProfitRate());
                    petsList.setSourceFrom((byte) 1);
                    petsList.setState((byte) 1);
                    petsList.setStartTime(DateUtils.getCurrentTimeStr());
                    petsList.setEndTime(DateUtils.getCurrentTimeStr());
                    petsList.setTransferUserId(-1);
                    petsList.setUserId(userId);
                    petsListService.insertSelective(petsList);
                    
                    //保存记录
                    Withdraw withdraw = new Withdraw();
                    withdraw.setAmount(amountBig);
                    withdraw.setCoinType(coinType.byteValue());
                    withdraw.setState((byte) GlobalParams.WITHDRAW_PAY);
                    withdraw.setUserId(userId);
                    withdrawService.insertSelective(withdraw);

                    accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_TYPE_ACTIVE, coinType, BigDecimalUtils.plusMinus(amountBig), BigDecimal.ZERO, userId, "提现兑换", petsList.getId());
                    return Result.toResult(ResultCode.SUCCESS);
                }
                return Result.toResult(ResultCode.WITHDRAW_ERROR);
            }else {
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
        }else{
            return Result.toResult(ResultCode.WITHDRAW_TIME_ERROR);
        }
    }

    private String checkAmount(String amount, Users users) {
        BigDecimal decimal = new BigDecimal(amount);
        String amountMin = sysparamsService.getValStringByKey(SystemParams.AMOUNT_WITHDRAW_MIN_AMOUNT);
        String amountMax = sysparamsService.getValStringByKey(Users.getWithdrawMaxAmount(users.getTeamLevel().intValue()));
        String numbers = sysparamsService.getValStringByKey(Users.getWithdrawNumber(users.getTeamLevel().intValue()));
        if(decimal.compareTo(new BigDecimal(amountMin)) > 0 && decimal.compareTo(new BigDecimal(amountMax)) <= 0){
            Map<Object, Object> param = new HashMap<>();
            String today = DateUtils.getCurrentDateStr();
            param.put("userId", users.getId());
            param.put("coinType", CoinType.CNY);
            param.put("time", today);
            int count = withdrawService.selectCounts(param);
            if(count < Integer.parseInt(numbers)){
                return null;
            }else{
                return Result.toResult(ResultCode.WITHDRAW_COUNT_LIMINT);
            }
        }
        return Result.toResult(ResultCode.WITHDRAW_ERROR);
    }

    @Override
    public String getAvailBalance(Users users, Integer coinType, byte accountType) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = users.getId();
        Account account = accountService.selectByUserIdAndAccountTypeAndType(accountType, coinType, userId);
        //贡献值
        result.put("contribution", users.getContribution());
        //mepc余额
        result.put("availbalance", account.getAvailbalance());
        //团队等级
        result.put("teamLevel", users.getTeamLevel());
        String holdAssets = petsListService.selectSumAmountByUser(userId);
        holdAssets = StrUtils.isBlank(holdAssets) ? "0" : holdAssets;
        //持有资产
        result.put("holdAssets", new BigDecimal(holdAssets).setScale(0, BigDecimal.ROUND_DOWN));
        String personProfit = flowService.selectPersonProfitSumAmount(userId, "个人收益");
        personProfit = StrUtils.isBlank(personProfit) ? "0" : personProfit;
        String teamProfit = profitRecordService.selectSumAmount(userId, 1);
        teamProfit = StrUtils.isBlank(teamProfit) ? "0" : teamProfit;
        //个人收益
        result.put("personProfit", new BigDecimal(personProfit).setScale(0, BigDecimal.ROUND_DOWN));
        account = accountService.selectByUserIdAndAccountTypeAndType(accountType, CoinType.CNY, userId);
        //团队收益
        result.put("teamProfit", account.getAvailbalance().setScale(0, BigDecimal.ROUND_DOWN));

        /*领养记录数量*/
        Map<Object, Object> params = new HashMap<>();
        params.put("buyUserId", userId);
        params.put("state", GlobalParams.PET_MATCHING_STATE_NOPAY);
        int appointmentCount = petsMatchingListService.selectCount(params);
        params.put("state", GlobalParams.PET_MATCHING_STATE_PAYED);
        appointmentCount = appointmentCount + petsMatchingListService.selectCount(params);
        result.put("appointmentCount", appointmentCount);

        /*转让记录数量*/
        params = new HashMap<>();
        params.put("saleUserId", userId);
        params.put("state", GlobalParams.PET_MATCHING_STATE_NOPAY);
        int transferCount = petsMatchingListService.selectCount(params);
        params.put("state", GlobalParams.PET_MATCHING_STATE_PAYED);
        transferCount = transferCount + petsMatchingListService.selectCount(params);
        result.put("transferCount", transferCount);
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
    public String recharge(Users users, Integer coinType, String amount, String address, String password) throws Exception {
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
        WithdrawQuote withdrawQuote = withdrawQuoteService.selectByUser(userId);
        String amount;
        if(withdrawQuote == null){
            amount = "0";
        }else{
            amount = withdrawQuote.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
        }
        return Result.toResult(ResultCode.SUCCESS, amount);
    }
}