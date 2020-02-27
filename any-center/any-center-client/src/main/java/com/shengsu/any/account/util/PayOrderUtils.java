package com.shengsu.any.account.util;

import com.shengsu.any.account.entity.PayOrder;

import java.math.BigDecimal;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-17 14:02
 **/
public class PayOrderUtils {
    public static PayOrder toPayOrder(String accountId,String orderNo,String prepayId,BigDecimal amount,String payType,String status) {
        PayOrder payOrder = new PayOrder();
        payOrder.setAccountId(accountId);
        payOrder.setOrderNo(orderNo);
        payOrder.setPrepayId(prepayId);
        payOrder.setAmount(amount);
        payOrder.setPayType(payType);
        payOrder.setStatus(status);
        return payOrder;
    }
}
