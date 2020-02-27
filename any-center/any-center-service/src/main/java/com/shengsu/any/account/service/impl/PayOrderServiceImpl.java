package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.entity.PayOrder;
import com.shengsu.any.account.mapper.PayOrderMapper;
import com.shengsu.any.account.service.PayOrderService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-27 11:06
 **/
@Service("payOrderService")
public class PayOrderServiceImpl extends BaseServiceImpl<PayOrder, String> implements PayOrderService {
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Override
    public BaseMapper<PayOrder, String> getBaseMapper() {
        return payOrderMapper;
    }
}
