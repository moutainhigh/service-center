package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.PayOrder;
import com.shengsu.any.account.mapper.PayOrderMapper;
import com.shengsu.any.account.service.PayOrderService;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultBean create(PayOrder payOrder) {
        save(payOrder);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean updateOrder(PayOrder payOrder) {
        payOrderMapper.updateOrder(payOrder);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public PayOrder getByOrderNo(String orderNo) {
        return payOrderMapper.getByOrderNo(orderNo);
    }
}
