package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.mapper.AccountMapper;
import com.shengsu.any.account.service.AccountServcie;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:02
 **/
@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl<Account, String> implements AccountServcie {
    @Autowired
    private AuthorizedService authorizedService;
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public BaseMapper<Account, String> getBaseMapper() {
        return accountMapper;
    }

    @Override
    public ResultBean getAccountBalance(String token) {
        if (token == null) {
            ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        UserDetailsPo user = authorizedService.getUserByToken(token);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        BigDecimal accountBalance = accountMapper.getAccountBalance(user.getUserId());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("accountBalance", accountBalance==null?new BigDecimal(0):accountBalance);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public List<Account> getAllAccountBalance(String userId) {
        return accountMapper.getAllAccountBalance(userId);
    }


}
