package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.PayOrder;
import com.shengsu.any.account.mapper.PayOrderMapper;
import com.shengsu.any.account.po.PayOrderListPo;
import com.shengsu.any.account.service.PayOrderService;
import com.shengsu.any.account.util.PayOrderUtils;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public ResultBean listPage(PayOrder payOrder) {
        Map<String, Object> map = new HashMap<>();
        payOrder.setSearch(StringUtil.ToLikeStr(payOrder.getSearch()));
        int totalCount = payOrderMapper.countAll(payOrder);
        if (totalCount > 0) {
            List<PayOrder> payOrders = payOrderMapper.listByPage(payOrder);
            List<PayOrderListPo> payOrderListPos = PayOrderUtils.toPayOrderListPos(payOrders);
            map.put("root", payOrderListPos);
            map.put("totalCount", totalCount);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }
}
