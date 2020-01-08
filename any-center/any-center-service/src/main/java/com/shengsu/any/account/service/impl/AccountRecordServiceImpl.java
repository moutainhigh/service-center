package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.mapper.AccountRecordMapper;
import com.shengsu.any.account.service.AccountRecordService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:25
 **/
@Service("accountRecordService")
public class AccountRecordServiceImpl extends BaseServiceImpl<AccountRecord, String> implements AccountRecordService{
    @Autowired
    private AccountRecordMapper accountRecordMapper;
    @Override
    public BaseMapper<AccountRecord, String> getBaseMapper() {
        return accountRecordMapper;
    }
}
