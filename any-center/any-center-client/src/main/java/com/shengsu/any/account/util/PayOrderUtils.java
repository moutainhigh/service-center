package com.shengsu.any.account.util;

import com.shengsu.any.account.entity.PayOrder;
import com.shengsu.any.account.po.PayOrderListPo;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.result.ResultBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.shengsu.any.user.util.UserUtils.toUserDetailsPo;

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
    public static PayOrder toPayOrder(String orderNo,String status,String transactionId,String completeTime) {
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderNo(orderNo);
        payOrder.setStatus(status);
        payOrder.setTransactionId(transactionId);
        payOrder.setCompleteTime(completeTime);
        return payOrder;
    }

    public static List<PayOrderListPo> toPayOrderListPos(List<PayOrder> payOrders) {
        if (payOrders != null) {
            List<PayOrderListPo> payOrderListPos = new ArrayList<>();
            for (PayOrder payOrder :payOrders) {
                PayOrderListPo payOrderListPo =toPayOrderListPo(payOrder);
                payOrderListPos.add(payOrderListPo);
            }
            return payOrderListPos;
        }
        return null;
    }

    private static PayOrderListPo toPayOrderListPo(PayOrder payOrder) {
        if (payOrder != null) {
            PayOrderListPo payOrderListPo = new PayOrderListPo();
            payOrderListPo.setAccountId(payOrder.getAccountId());
            payOrderListPo.setOrderNo(payOrder.getOrderNo());
            payOrderListPo.setAmount(payOrder.getAmount());
            payOrderListPo.setPayType(payOrder.getPayType());
            payOrderListPo.setOrderTime(payOrder.getOrderTime());
            payOrderListPo.setCompleteTime(payOrder.getCompleteTime());
            return payOrderListPo;
        }
        return null;
    }
}
