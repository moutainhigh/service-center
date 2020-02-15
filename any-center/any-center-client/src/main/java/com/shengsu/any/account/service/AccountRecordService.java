package com.shengsu.any.account.service;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:22
 **/
public interface AccountRecordService extends BaseService<AccountRecord,String> {
    ResultBean listIncome(String token);
    ResultBean listExpend(String token);
}
