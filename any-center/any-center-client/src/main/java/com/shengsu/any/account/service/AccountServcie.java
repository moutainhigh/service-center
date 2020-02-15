package com.shengsu.any.account.service;

import com.shengsu.any.account.entity.Account;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 09:59
 **/
public interface AccountServcie extends BaseService<Account,String> {
    ResultBean getAccountBalance(String token);
}
