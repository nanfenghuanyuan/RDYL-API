package com.zh.module.biz.impl;

import com.zh.module.biz.RpcBiz;
import com.zh.module.biz.impl.BaseBizImpl;
import com.zh.module.constants.AccountType;
import com.zh.module.constants.CoinType;
import com.zh.module.constants.GlobalParams;
import com.zh.module.constants.SystemParams;
import com.zh.module.dto.Result;
import com.zh.module.entity.SmsRecord;
import com.zh.module.entity.Sysparams;
import com.zh.module.entity.Users;
import com.zh.module.enums.ResultCode;
import com.zh.module.service.AccountService;
import com.zh.module.service.SmsRecordService;
import com.zh.module.service.SysparamsService;
import com.zh.module.service.UsersService;
import com.zh.module.utils.BigDecimalUtils;
import com.zh.module.utils.HTTP;
import com.zh.module.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
    @Override
    public String transfer(Users user, String phone, String amount, String code, Integer codeId) {
        /*校验功能开关*/
        String transferOnOff = sysparamsService.getValStringByKey(SystemParams.RPC_TRANSFER_ON_OFF);
        if(StrUtils.isBlank(transferOnOff) || !"1".equals(transferOnOff)){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        /*校验验证码是否正确*/
        SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, user.getPhone());
        if(sms == null || !code.equals(sms.getCode())){
            if(validateErrorTimesOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }else{
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
        }

        /*校验验证码有效期*/
        Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
        int interval = (int) ((System.currentTimeMillis() - sms.getCreateTime().getTime()) / (1000*60));
        if(timeLimit==null || sms.getTimes()!= GlobalParams.ACTIVE|| interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
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
}
