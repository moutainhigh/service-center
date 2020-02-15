package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.mapper.AccountRecordMapper;
import com.shengsu.any.account.po.ExpendListPo;
import com.shengsu.any.account.po.IncomeListPo;
import com.shengsu.any.account.service.AccountRecordService;
import com.shengsu.any.account.util.AccountRecordUtils;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:25
 **/
@Service("accountRecordService")
public class AccountRecordServiceImpl extends BaseServiceImpl<AccountRecord, String> implements AccountRecordService{
    @Autowired
    private AuthorizedService authorizedService;
    @Autowired
    private AccountRecordMapper accountRecordMapper;
    @Override
    public BaseMapper<AccountRecord, String> getBaseMapper() {
        return accountRecordMapper;
    }

    @Override
    public ResultBean listIncome(String token) {
        if (token == null) {
            ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        UserDetailsPo user = authorizedService.getUserByToken(token);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        List<AccountRecord> accountRecords = accountRecordMapper.listIncome(user.getUserId());
        List<IncomeListPo> result = new ArrayList<>();
        for (AccountRecord accountRecord : accountRecords){
            IncomeListPo incomeListPo = AccountRecordUtils.toIncomeListPo(accountRecord);
            result.add(incomeListPo);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
    }

    @Override
    public ResultBean listExpend(String token) {
        if (token == null) {
            ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        UserDetailsPo user = authorizedService.getUserByToken(token);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        List<AccountRecord> accountRecords = accountRecordMapper.listExpend(user.getUserId());
        List<ExpendListPo> result = new ArrayList<>();
        for (AccountRecord accountRecord : accountRecords){
            ExpendListPo expendListPo = AccountRecordUtils.toExpendListPo(accountRecord);
            result.add(expendListPo);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
    }
}
