package com.zh.module.biz.impl;

import com.zh.module.biz.PetsListBiz;
import com.zh.module.biz.PetsMatchingListBiz;
import com.zh.module.constants.*;
import com.zh.module.dto.Result;
import com.zh.module.entity.*;
import com.zh.module.enums.ResultCode;
import com.zh.module.enums.RewardType;
import com.zh.module.exception.BanlanceNotEnoughException;
import com.zh.module.model.PageModel;
import com.zh.module.model.PayInfoModel;
import com.zh.module.model.PetsMatchingListModel;
import com.zh.module.model.PetsOrderModel;
import com.zh.module.service.*;
import com.zh.module.utils.*;
import com.zh.module.variables.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @program: R.D.Y.LMain
 * @description:
 * @author: zhaohe
 * @create: 2019-12-19 20:06
 **/
@Component
@Transactional
public class PetsListListBizImpl extends BaseBizImpl implements PetsListBiz {
    @Autowired
    private PetsMatchingListService petsMatchingListService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private PetsListService petsListService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private TeamRecordService teamRecordService;
    @Autowired
    private TeamAwardRecordService teamAwardRecordService;
    @Autowired
    private RedisTemplate<String,String> redis;

    @Override
    public String list(Users users, Integer state, PageModel pageModel) {
        Map<Object, Object> param = new HashMap<>();
        param.put("state", state);
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<Map<String, Object>> lists = petsListService.selectListPaging(param);
        List<PetsMatchingListModel> listModels = new LinkedList<>();
        String transferTime;
        BigDecimal price;
        for(Map<String, Object> map : lists){
            PetsMatchingListModel petsMatchingListModel = new PetsMatchingListModel();
            petsMatchingListModel.setId((Integer) map.get("id"));
            petsMatchingListModel.setImgUrl(map.get("img_url").toString());
            petsMatchingListModel.setName(map.get("name").toString());
            price = new BigDecimal(map.get("price").toString());
            petsMatchingListModel.setPrice(price);
            petsMatchingListModel.setState(state);
            if(state == GlobalParams.PET_LIST_STATE_WAIT){
                petsMatchingListModel.setAppointmentTime(map.get("start_time").toString());
            }else{
                transferTime = DateUtils.getDateFormate((Date) map.get("update_time"));
                petsMatchingListModel.setTransferTime(transferTime);
            }
            petsMatchingListModel.setProfited(price.multiply(new BigDecimal(map.get("profit_rate").toString()).setScale(2, BigDecimal.ROUND_HALF_UP)));
            petsMatchingListModel.setProfit(map.get("profit_days").toString() + "天/" + new BigDecimal(map.get("profit_rate").toString()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            listModels.add(petsMatchingListModel);
        }
        return Result.toResult(ResultCode.SUCCESS, listModels);
    }

    @Override
    public String get(Users users, Integer id) {
        PetsMatchingList petsMatchingList = petsMatchingListService.selectByPetListIdAndActive(id);
        if(petsMatchingList == null){
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }
        boolean isBuyer = false;
        Pets pets = petsService.selectByLevel(petsMatchingList.getLevel().intValue());
        PetsList petsList = petsListService.selectByPrimaryKey(petsMatchingList.getPetListId().intValue());
        if(users.getId().equals(petsMatchingList.getBuyUserId())){
            isBuyer = true;
        }
        PetsOrderModel petsOrderModel = new PetsOrderModel();

        List<BindInfo> bindInfos = new ArrayList<>();
        List<PayInfoModel> payInfoModels = new ArrayList<>();
        //1未付款 2未确认
        int state = petsMatchingList.getState().intValue();
        int btnType;
        boolean cancelBtn = false;
        if(isBuyer) {
            Users saleUser = usersService.selectByPrimaryKey(petsMatchingList.getSaleUserId());
            petsOrderModel.setBuyName(users.getNickName());
            petsOrderModel.setBuyPhone(users.getPhone());
            petsOrderModel.setSaleName(saleUser.getNickName());
            petsOrderModel.setSalePhone(saleUser.getPhone());
            bindInfos = bindInfoService.queryByUser(petsMatchingList.getSaleUserId());
            if(state == 1){
                btnType = GlobalParams.ORDER_BTN_TYPE_NOPAY;
            }else{
                btnType = GlobalParams.ORDER_BTN_TYPE_WAIT_CONFIRM;
            }
            cancelBtn = true;
        }else{
            Users saleUser = usersService.selectByPrimaryKey(petsMatchingList.getBuyUserId());
            petsOrderModel.setBuyName(saleUser.getNickName());
            petsOrderModel.setBuyPhone(saleUser.getPhone());
            petsOrderModel.setSaleName(users.getNickName());
            petsOrderModel.setSalePhone(users.getPhone());
            bindInfos = bindInfoService.queryByUser(users.getId());
            btnType = GlobalParams.ORDER_BTN_TYPE_CONFIRM;
        }
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
        petsOrderModel.setBtnType(btnType);
        petsOrderModel.setCancelBtn(cancelBtn);
        petsOrderModel.setPayInfoModels(payInfoModels);
        petsOrderModel.setName(pets.getName());
        petsOrderModel.setPrice(petsList.getPrice());
        petsOrderModel.setProfit(petsList.getProfitDays() + "天/" + new BigDecimal(petsList.getProfitRate().toString()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
        petsOrderModel.setTransTime(DateUtils.getDateFormate(petsList.getCreateTime()));
        petsOrderModel.setPayTime(petsMatchingList.getPayTime());
        petsOrderModel.setState(state);
        return Result.toResult(ResultCode.SUCCESS, petsOrderModel);
    }

    @Override
    public String confirmPay(Users users, Integer id, String password) {
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        PetsList petsList = petsListService.selectByPrimaryKey(id);
        PetsMatchingList petsMatchingList = petsMatchingListService.selectByPetListIdAndActive(id);
        //判断订单状态 仅有转让中和未确认未付款状态可行
        if(petsList == null || petsList.getState() != GlobalParams.PET_LIST_STATE_WAITING || petsMatchingList == null){
            return Result.toResult(ResultCode.PETS_STATE_ERROR);
        }
        if(petsMatchingList.getState() != GlobalParams.PET_MATCHING_STATE_NOPAY){
            return Result.toResult(ResultCode.PETS_STATE_ERROR);
        }
        //仅买家可以操作
        if(!petsMatchingList.getBuyUserId().equals(users.getId())){
            return Result.toResult(ResultCode.OPERATOR_NOT_LIMIT);
        }
        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(users, password);
            if(valiStr != null){
                return valiStr;
            }
        }
        petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_PAYED);
        petsMatchingList.setPayTime(DateUtils.getCurrentTimeStr());

        /*设置失效时间*/
        int interval = 10;
        Sysparams param1 = sysparamsService.getValByKey(SystemParams.PETS_MATCHING_NO_CONFIRM_CANCEL_TIME);
        if(param1 != null){
            interval = Integer.parseInt(param1.getKeyval());
        }
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MINUTE, interval);
        petsMatchingList.setInactiveTime(new Timestamp(current.getTimeInMillis()));
        petsMatchingListService.updateByPrimaryKeySelective(petsMatchingList);

        /*订单加入待确认队列*/
        addOverTimeQueue(petsMatchingList, RedisKey.MATCHING_NOTCONFIRM_KEY_NAME, RedisKey.MATHCHING_NOTCONFIRM, interval);

        /*短信通知卖家*/
        Users saleUser = usersService.selectByPrimaryKey(petsMatchingList.getSaleUserId());
        if(saleUser != null){
            FeigeSmsUtils feigeSmsUtils = new FeigeSmsUtils();
            feigeSmsUtils.sendTemplatesSms(saleUser.getPhone(), SmsTemplateCode.SMS_C2C_PAY_NOTICE, "");
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String confirmReceipt(Users users, Integer id, String password) {
        //验证用户状态
        if(!checkUserState(users)){
            return Result.toResult(ResultCode.USER_STATE_ERROR);
        }
        PetsList petsList = petsListService.selectByPrimaryKey(id);
        PetsMatchingList petsMatchingList = petsMatchingListService.selectByPetListIdAndActive(id);
        //判断订单状态 仅有转让中和未确认未付款状态可行
        if(petsList == null || petsList.getState() != GlobalParams.PET_LIST_STATE_WAITING || petsMatchingList == null){
            return Result.toResult(ResultCode.PETS_STATE_ERROR);
        }
        if(petsMatchingList.getState() != GlobalParams.PET_MATCHING_STATE_PAYED){
            return Result.toResult(ResultCode.PETS_STATE_ERROR);
        }
        //仅卖家可以操作
        if(!petsMatchingList.getSaleUserId().equals(users.getId())){
            return Result.toResult(ResultCode.OPERATOR_NOT_LIMIT);
        }
        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(users, password);
            if(valiStr != null){
                return valiStr;
            }
        }
        //修改宠物记录
        petsList.setUserId(users.getId());
        petsList.setTransferUserId(-1);
        petsList.setState((byte) GlobalParams.PET_LIST_STATE_PROFITING);
        petsList.setStartTime(DateUtils.getCurrentTimeStr());
        petsList.setEndTime(DateUtils.getSomeDay(petsList.getProfitDays()));
        petsListService.updateByPrimaryKeySelective(petsList);

        //修改匹配记录
        petsMatchingList.setState((byte) GlobalParams.PET_MATCHING_STATE_COMPLIETE);
        petsMatchingListService.updateByPrimaryKeySelective(petsMatchingList);

        /*短信通知买家*/
        Integer buyUserId = petsMatchingList.getBuyUserId();
        Users buyUser = usersService.selectByPrimaryKey(buyUserId);
        if(buyUser != null){
            FeigeSmsUtils feigeSmsUtils = new FeigeSmsUtils();
            feigeSmsUtils.sendTemplatesSms(buyUser.getPhone(), SmsTemplateCode.SMS_C2C_CONFIRM_NOTICE, "");
            //增加用户贡献值
            buyUser.setContribution(buyUser.getContribution() + 1);
            usersService.updateByPrimaryKeySelective(buyUser);
            //团队奖励
            String profit = sysparamsService.getValStringByKey(SystemParams.PERSON_AWARD_ONE);
            //推荐代数
            int cursor = 1;
            //团队累计奖励
            BigDecimal awardTotal = BigDecimal.ZERO;
            users = usersService.selectByUUID(buyUser.getReferId());
            referLevelAward(users, petsList.getPrice(), new BigDecimal(profit), cursor, awardTotal);
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    /**
     * 推荐奖励
     * @param users
     * @param amount 订单价格
     * @param rate
     * @param cursor
     */
    private void referLevelAward(Users users, BigDecimal amount, BigDecimal rate, Integer cursor, BigDecimal awardTotal) {
        if(users == null){
            return;
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

            TeamRecord teamRecord = new TeamRecord();
            teamRecord.setAmount(newAmount);
            teamRecord.setType(cursor.byteValue());
            teamRecord.setReferId(users.getReferId());
            teamRecord.setUserId(users.getId());
            teamRecordService.insertSelective(teamRecord);
            //团队奖励记录累计金额
            if (cursor > RewardType.PERSON_AWARD_TWO.code()) {
                teamReward(users.getId(), newAmount);
            }
        }
            users = usersService.selectByUUID(users.getReferId());
            if (cursor > RewardType.TEAM_AWARD_TWO.code()) {
                cursor = 5;
            } else {
                cursor++;
            }
            String price = sysparamsService.getValStringByKey(RewardType.getMessage(cursor));
            referLevelAward(users, amount, new BigDecimal(price), cursor, awardTotal);

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
     * 将订单加入到超时队列中， （名称list保存订单队列的key集合，订单队列保存订单集合）
     * @param nameListKey nameList的key
     * @param queueOrderKey 订单队列的key
     * @param timeout 超时时间
     * @return void
     * @date 2018-3-7
     * @author lina
     */
    public void addOverTimeQueue(PetsMatchingList petsMatchingList ,String nameListKey, String queueOrderKey,int timeout){
        /*从nameList的最右侧取出最新的queueKey,如果不存在则生成新的queueKey,并添加到nameList中*/
        String keyName = "";
        long keySize = RedisUtil.searchListSize(redis, nameListKey);
        if(keySize == 0){
            keyName = String.format(queueOrderKey, timeout);
            RedisUtil.addListRight(redis, nameListKey, keyName);
        }else{
            keyName = RedisUtil.searchIndexList(redis,nameListKey,keySize-1);
            String keyNameNew = String.format(queueOrderKey, timeout);
            if(!keyName.equals(keyNameNew)){
                keyName = keyNameNew;
                RedisUtil.addListRight(redis, nameListKey, keyName);
            }
        }
        /*订单加入订单队列中*/
        RedisUtil.addListRight(redis, keyName, petsMatchingList);
    }

    @Override
    public void getProfit() {
        String date = DateUtils.getCurrentTimeStr();
        Map<Object, Object> param = new HashMap<>();
        param.put("state", GlobalParams.PET_LIST_STATE_PROFITING);
        param.put("endTime", date);
        List<PetsList> petsLists = petsListService.selectDoProfit(param);
        Pets pets = new Pets();
        Pets newPets = new Pets();
        Flow flow = new Flow();
        BigDecimal profitAmount;
        BigDecimal priceMax;
        Account account;
        for(PetsList petsList : petsLists){
            pets = petsService.selectByLevel(petsList.getLevel().intValue());
            profitAmount = petsList.getProfitRate().multiply(petsList.getPrice());
            priceMax = pets.getPriceMix();
            //如果收益后价格超出上限，则宠物升级
            if(petsList.getPrice().add(profitAmount).compareTo(priceMax) > 0){
                newPets = petsService.selectByLevel(pets.getUpgradeId().intValue());
                petsList.setLevel(newPets.getLevel());
                petsList.setProfitCoin(newPets.getProfitCoin());
                petsList.setProfitCoinRate(newPets.getProfitCoinRate());
                petsList.setProfitDays(newPets.getProfitDays());
                petsList.setProfitRate(newPets.getProfitRate());
            }
            petsList.setPrice(petsList.getPrice().add(profitAmount));
            petsList.setState((byte) GlobalParams.PET_LIST_STATE_WAIT);
            petsListService.updateByPrimaryKeySelective(petsList);

            account = accountService.selectByUserIdAndAccountTypeAndType(AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.CNY, petsList.getUserId());
            //插入收益流水
            flow.setUserId(petsList.getUserId());
            flow.setRelateId(petsList.getId());
            flow.setOperType("合约收益");
            flow.setOperId(petsList.getUserId());
            flow.setCoinType(CoinType.CNY);
            flow.setAccountType(AccountType.ACCOUNT_TYPE_ACTIVE);
            flow.setAmount(profitAmount);
            flow.setResultAmount(account.getAvailbalance().add(profitAmount).toPlainString());
            flowService.insertSelective(flow);
        }
    }
}