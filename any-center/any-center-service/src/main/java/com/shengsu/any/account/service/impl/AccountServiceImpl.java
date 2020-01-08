package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.mapper.AccountMapper;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.any.user.service.UserService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:02
 **/
@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl<Account, String> implements AccountServcie {
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public BaseMapper<Account, String> getBaseMapper() {
        return accountMapper;
    }
}
