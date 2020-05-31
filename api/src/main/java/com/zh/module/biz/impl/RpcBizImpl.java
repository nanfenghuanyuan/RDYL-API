package com.zh.module.biz.impl;

import com.zh.module.biz.RpcBiz;
import com.zh.module.biz.impl.BaseBizImpl;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.CoinManager;
import com.zh.module.entity.SmsRecord;
import com.zh.module.entity.Sysparams;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.service.*;
import com.zh.module.utils.BigDecimalUtils;
import com.zh.module.utils.DateUtils;
import com.zh.module.utils.HTTP;
import com.zh.module.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class RpcBizImpl extends BaseBizImpl implements RpcBiz {

    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private CoinManagerService coinManagerService;
    @Override
    public String transfer(Users user, String phone, String amount, String password) throws Exception {
        /*校验功能开关*/
        String transferOnOff = sysparamsService.getValStringByKey(SystemParams.RPC_TRANSFER_ON_OFF);
        if(StrUtils.isBlank(transferOnOff) || !"1".equals(transferOnOff)){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }
        accountService.updateAccountAndInsertFlow(user.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, BigDecimalUtils.plusMinus(new BigDecimal(amount)),
                BigDecimal.ZERO, user.getId(), "MEPC跨平台提取", 1);
        String url = "http://118.190.146.100:8081/rpc/transferIn";
        Map<String, String> param = new HashMap<>();
        param.put("phone", phone);
        param.put("amount", amount);
        String result = "";
        try {
            result = HTTP.postFrom(url, null, param);
        } catch (Exception e) {
            return Result.toResult(ResultCode.INTERFACE_ADDRESS_INVALID);
        }
        return result;
    }

    @Override
    public String transferIn(String phone, String amount) {
        Users user = usersService.selectByPhone(phone);
        accountService.updateAccountAndInsertFlow(user.getId(), AccountType.ACCOUNT_TYPE_ACTIVE, CoinType.OS, new BigDecimal(amount),
                BigDecimal.ZERO, user.getId(), "MEPC跨平台转入", 1);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String transferList(Users user, Integer page, Integer rows) {
        Integer pageInt = page == null ? 0 : page;
        Integer rowsInt = rows == null ? 10 : rows;
        List<Map<String, Object>> flows = flowService.selectByTransferList(user.getId(), pageInt, rowsInt);
        List<Map<String, Object>> list = new LinkedList<>();
        for(Map<String, Object> flow : flows){
            Map<String, Object> data = new HashMap<>();
            String coinType = CoinType.getCoinName(Integer.parseInt(flow.get("coin_type").toString()));
            CoinManager coinManage = coinManagerService.queryByCoinType(Integer.parseInt(flow.get("coin_type").toString()));
            data.put("coinType", coinType);
            data.put("coinImgUrl", "");
            data.put("time", DateUtils.getDateFormate((Date) flow.get("create_time")));
            data.put("operate", flow.get("oper_type"));
            BigDecimal amount = new BigDecimal(flow.get("amount").toString());
            if(amount.compareTo(BigDecimal.ZERO) > 0){
                data.put("amountSize", true);
                data.put("amount", "+" + amount.stripTrailingZeros().toPlainString() + " " + coinType);
            }else{
                data.put("amountSize", false);
                data.put("amount", amount.stripTrailingZeros().toPlainString() + " " + coinType);
            }
            list.add(data);
        }
        return Result.toResult(ResultCode.SUCCESS, list);
    }
}
