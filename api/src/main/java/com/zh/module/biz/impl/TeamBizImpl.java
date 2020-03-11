package com.zh.module.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.module.biz.TeamBiz;
import com.zh.module.constants.*;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.enums.RewardType;
import com.zh.module.model.PageModel;
import com.zh.module.model.TeamListModel;
import com.zh.module.service.*;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.StrUtils;
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
public class TeamBizImpl implements TeamBiz {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private TeamRecordService teamRecordService;
    @Autowired
    private TeamAwardRecordService teamAwardRecordService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private ProfitRecordService profitRecordService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private WithdrawQuoteService withdrawQuoteService;
    @Autowired
    private FlowService flowService;


    @Override
    public String init(Users users, Integer type, PageModel pageModel) {
        Map<String, Object> result = new HashMap<>();
        Map<Object, Object> param = new HashMap<>();
        //团队
        JSONObject jsonObject = getTeam(new JSONObject(), users.getUuid());
        List<Integer> team = new LinkedList<>();
        team.add(jsonObject.getIntValue("allNumber"));
        team.add(jsonObject.getIntValue("activeNumber"));
        team.add(jsonObject.getIntValue("effectiveNumber"));
        result.put("team", team);
        //一级
        List<Integer> one = getOne(users.getUuid());
        result.put("one", one);
        //二级
        List<Integer> two = getTwo(users.getUuid());
        result.put("two", two);
        List<TeamListModel> models = new LinkedList<>();
        param = new HashMap<>();
        param.put("referId", users.getUuid());
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<Users> usersList = usersService.selectPaging(param);
        if(type == 1){
            for(Users user : usersList){
                TeamListModel teamListModel = new TeamListModel();
                teamListModel.setName(user.getNickName());
                teamListModel.setTime(DateUtils.getDateFormate2(user.getCreateTime()));
                teamListModel.setPhone(user.getPhone());
                teamListModel.setIdStatus(user.getState().intValue());
                models.add(teamListModel);
            }
        }else if(type == 2){
            for(Users user : usersList){
                param = new HashMap<>();
                param.put("referId", user.getUuid());
                List<Users> referUserList = usersService.selectAll(param);
                for(Users referUser : referUserList) {
                    TeamListModel teamListModel = new TeamListModel();
                    teamListModel.setName(referUser.getNickName());
                    teamListModel.setTime(DateUtils.getDateFormate2(referUser.getCreateTime()));
                    teamListModel.setPhone(referUser.getPhone());
                    teamListModel.setIdStatus(referUser.getState().intValue());
                    models.add(teamListModel);
                }
            }
        }else{
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }
        result.put("list", models);
        return Result.toResult(ResultCode.SUCCESS, result);
    }

    private List<Integer> getTwo(String uuid) {
        List<Integer> integers = new LinkedList<>();
        List<Users> list = usersService.selectByReferID(uuid);
        int activeNumber = 0;
        int effectiveNumber = 0;
        int allNumber = 0;
        for(Users users : list){
            List<Users> lists = usersService.selectByReferID(users.getUuid());
            for(Users users1: lists){
                if(users1.getIdStatus() == GlobalParams.ACTIVE){
                    activeNumber ++;
                }
                if(users1.getEffective() == GlobalParams.ACTIVE){
                    effectiveNumber ++;
                }
                allNumber ++;
            }
        }
        integers.add(allNumber);
        integers.add(activeNumber);
        integers.add(effectiveNumber);
        return integers;
    }

    private List<Integer> getOne(String uuid) {
        List<Integer> integers = new LinkedList<>();
        List<Users> list = usersService.selectByReferID(uuid);
        int activeNumber = 0;
        int effectiveNumber = 0;
        for(Users users : list){
            if(users.getIdStatus() == GlobalParams.ACTIVE){
                activeNumber ++;
            }
            if(users.getEffective() == GlobalParams.ACTIVE){
                effectiveNumber ++;
            }
        }
        integers.add(list.size());
        integers.add(activeNumber);
        integers.add(effectiveNumber);
        return integers;
    }

    private JSONObject getTeam(JSONObject json, String uuid){

        List<Users> list = usersService.selectByReferID(uuid);
        if(list.size() == 0){
            return json;
        }
        for(Users users : list){
            int allNumber = 0;
            int activeNumber = 0;
            int effectiveNumber = 0;
            if(users.getIdStatus() == GlobalParams.ACTIVE){
                activeNumber ++;
            }
            if(users.getEffective() == GlobalParams.ACTIVE){
                effectiveNumber ++;
            }
            allNumber ++;
            json.put("allNumber", json.getIntValue("allNumber") + allNumber);
            json.put("activeNumber", json.getIntValue("activeNumber") + activeNumber);
            json.put("effectiveNumber", json.getIntValue("effectiveNumber") + effectiveNumber);
            getTeam(json, users.getUuid());
        }
        return json;
    }

    @Override
    public void getProfit() {
        Map<Object, Object> params = new HashMap<>();
        params.put("state", GlobalParams.PET_MATCHING_STATE_OVER);
        params.put("start", DateUtils.getCurrentDateStr() + " 00:00:00");
        params.put("end", DateUtils.getCurrentDateStr() + " 23:59:59");
        List<PetsMatchingList> petsMatchingLists = petsMatchingListService.selectByTime(params);
        for(PetsMatchingList petsMatchingList : petsMatchingLists){
            PetsList petsList = petsListService.selectByPrimaryKey(petsMatchingList.getPetListId());
            //买家
            Users buyUser = usersService.selectByPrimaryKey(petsMatchingList.getBuyUserId());
            //团队奖励
            String profit = sysparamsService.getValStringByKey(SystemParams.PERSON_AWARD_ONE);
            //推荐代数
            int cursor = 1;
            //团队累计奖励
            BigDecimal awardTotal = BigDecimal.ZERO;
            Users users = usersService.selectByUUID(buyUser.getReferId());
            referLevelAward(users, petsList.getPrice().multiply(petsList.getProfitRate()), new BigDecimal(profit), cursor, awardTotal, petsList.getPetsNumber());
            //用户等级提升
            userLevel(buyUser);
            //增加提现额度
            changeWithdrawQuota(buyUser.getId(), petsList.getPrice());
        }
    }
    /**
     * 推荐奖励
     * @param users
     * @param amount 订单价格
     * @param rate
     * @param cursor
     */
    private void referLevelAward(Users users, BigDecimal amount, BigDecimal rate, Integer cursor, BigDecimal awardTotal, String petsNum) {
        if(users == null){
            return;
        }
        if(users.getTeamLevel() == GlobalParams.TEAM_LEVEL_0){
            if(cursor > RewardType.PERSON_AWARD_TWO.code()){
                return;
            }
        }
        //用户状态可用时才有收益
        if(users.getState() == GlobalParams.ACTIVE && users.getIdStatus() == GlobalParams.ACTIVE) {
            //团队 大于等于同级别拿团队1%  小于等级拿之差
            if (cursor > RewardType.PERSON_AWARD_TWO.code()) {
                rate = rate.subtract(awardTotal);
                if (rate.compareTo(BigDecimal.ZERO) <= 0) {
                    rate = new BigDecimal(0.01);
                }
                awardTotal = awardTotal.add(rate);
            }
            //奖励金额
            BigDecimal newAmount = amount.multiply(rate);

            //保存收益记录
            TeamRecord teamRecord = new TeamRecord();
            teamRecord.setAmount(newAmount);
            teamRecord.setType(cursor.byteValue());
            teamRecord.setReferId(users.getReferId());
            teamRecord.setUserId(users.getId());
            teamRecordService.insertSelective(teamRecord);

            ProfitRecord profitRecord = new ProfitRecord();
            profitRecord.setAmount(newAmount);
            profitRecord.setRemark(petsNum);
            profitRecord.setType((byte) 1);
            profitRecord.setUserId(users.getId());
            profitRecordService.insertSelective(profitRecord);
            //插入流水
            accountService.updateAccountAndInsertFlow(users.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.CNY, newAmount, BigDecimal.ZERO, users.getId(), "推荐收益", teamRecord.getId());
            //团队奖励记录累计金额
            if (cursor > RewardType.PERSON_AWARD_TWO.code()) {
                teamReward(users.getId(), newAmount);
            }
        }
        //用户上级推荐人
        users = usersService.selectByUUID(users.getReferId());
        if (cursor > RewardType.TEAM_AWARD_TWO.code()) {
            cursor = 5;
        } else {
            cursor++;
        }
        //当前推荐等级收益率
        String price = sysparamsService.getValStringByKey(RewardType.getMessage(cursor));
        //递归调用
        referLevelAward(users, amount, new BigDecimal(price), cursor, awardTotal, petsNum);

    }

    /**
     * 团队奖励记录累计金额
     * @param userId 用户id
     * @param amount 奖励金额
     */
    private void teamReward(Integer userId, BigDecimal amount){
        TeamAwardRecord teamAwardRecord = teamAwardRecordService.selectByUserId(userId);
        if(teamAwardRecord == null){
            teamAwardRecord = new TeamAwardRecord();
            teamAwardRecord.setUserId(userId);
            teamAwardRecord.setAmount(amount);
        }else{
            teamAwardRecord.setAmount(teamAwardRecord.getAmount().add(amount));
        }
        teamAwardRecordService.updateOrInsert(teamAwardRecord);
    }
    /**
     * 等級提升
     * @param buyUser
     */
    private void userLevel(Users buyUser) {
        List<Users> oneList = usersService.selectByReferIDAndActive(buyUser.getUuid());
        int ones = oneList.size();
        int twos = getTwo2(buyUser.getUuid());
        TeamRecord teamRecord = teamRecordService.selectByUser(buyUser.getId());
        String teamLevelOne = sysparamsService.getValStringByKey(SystemParams.TEAM_LEVEL_ONE);
        String teamLevelTwo = sysparamsService.getValStringByKey(SystemParams.TEAM_LEVEL_TWO);
        String teamLevelThree = sysparamsService.getValStringByKey(SystemParams.TEAM_LEVEL_THREE);
        if(teamRecord != null) {
            if (buyUser.getTeamLevel() == GlobalParams.TEAM_LEVEL_0) {
                if (ones >= 50 && twos >= 120 && teamRecord.getAmount().compareTo(new BigDecimal(teamLevelThree)) >= 0) {
                    buyUser.setTeamLevel((byte) GlobalParams.TEAM_LEVEL_3);
                    usersService.updateByPrimaryKeySelective(buyUser);
                } else if (ones >= 30 && twos >= 60 && teamRecord.getAmount().compareTo(new BigDecimal(teamLevelTwo)) >= 0) {
                    buyUser.setTeamLevel((byte) GlobalParams.TEAM_LEVEL_2);
                    usersService.updateByPrimaryKeySelective(buyUser);
                } else if (ones >= 10 && twos >= 30 && teamRecord.getAmount().compareTo(new BigDecimal(teamLevelOne)) >= 0) {
                    buyUser.setTeamLevel((byte) GlobalParams.TEAM_LEVEL_1);
                    usersService.updateByPrimaryKeySelective(buyUser);
                }
            }
            if (buyUser.getTeamLevel() == GlobalParams.TEAM_LEVEL_1) {
                if (ones >= 50 && twos >= 120 && teamRecord.getAmount().compareTo(new BigDecimal(teamLevelThree)) >= 0) {
                    buyUser.setTeamLevel((byte) GlobalParams.TEAM_LEVEL_3);
                    usersService.updateByPrimaryKeySelective(buyUser);
                } else if (ones >= 30 && twos >= 60 && teamRecord.getAmount().compareTo(new BigDecimal(teamLevelTwo)) >= 0) {
                    buyUser.setTeamLevel((byte) GlobalParams.TEAM_LEVEL_2);
                    usersService.updateByPrimaryKeySelective(buyUser);
                }
            }
            if (buyUser.getTeamLevel() == GlobalParams.TEAM_LEVEL_2) {
                if (ones >= 50 && twos >= 120 && teamRecord.getAmount().compareTo(new BigDecimal(teamLevelThree)) >= 0) {
                    buyUser.setTeamLevel((byte) GlobalParams.TEAM_LEVEL_3);
                    usersService.updateByPrimaryKeySelective(buyUser);
                }
            }
        }
    }
    private Integer getTwo2(String uuid) {
        List<Users> list = usersService.selectByReferIDAndActive(uuid);
        int allNumber = 0;
        for(Users users : list){
            List<Users> lists = usersService.selectByReferIDAndActive(users.getUuid());
            for(Users users1: lists){
                allNumber ++;
            }
        }
        return allNumber;
    }
    /**
     * 增加提现额度
     * @param userId
     * @param amount
     */
    private void changeWithdrawQuota(Integer userId, BigDecimal amount) {
        String withdrawRadio = sysparamsService.getValStringByKey(SystemParams.WITHDRAW_QUOTA_RADIO);
        amount = amount.multiply(new BigDecimal(withdrawRadio));
        WithdrawQuote withdrawQuote = withdrawQuoteService.selectByUser(userId);
        if(withdrawQuote == null){
            withdrawQuote = new WithdrawQuote();
            withdrawQuote.setAmount(amount);
            withdrawQuote.setCoinType((byte) CoinType.CNY);
            withdrawQuote.setUserId(userId);
            withdrawQuoteService.insertSelective(withdrawQuote);
        }else{
            withdrawQuote.setAmount(withdrawQuote.getAmount().add(amount));
            withdrawQuoteService.updateByPrimaryKeySelective(withdrawQuote);
        }
        Flow flow = new Flow();
        flow.setAccountType(AccountType.ACCOUNT_TYPE_ACTIVE);
        flow.setAmount(amount);
        flow.setCoinType(CoinType.CNY);
        flow.setOperId(userId);
        flow.setOperType("提现增加额度");
        flow.setRelateId(withdrawQuote.getId());
        flow.setResultAmount(withdrawQuote.getAmount().toPlainString());
        flow.setUserId(userId);
        flowService.insertSelective(flow);
    }

}