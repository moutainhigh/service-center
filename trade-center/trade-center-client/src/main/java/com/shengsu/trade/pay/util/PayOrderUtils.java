package com.shengsu.trade.pay.util;


import com.shengsu.helper.entity.SystemDict;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.po.PayOrderListPo;
import com.shengsu.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-17 14:02
 **/
public class PayOrderUtils {
    public static PayOrder toPayOrder(String accountId, String orderNo, String prepayId, BigDecimal amount, String payType, String status) {
        PayOrder payOrder = new PayOrder();
        payOrder.setAccountId(accountId);
        payOrder.setOrderNo(orderNo);
        payOrder.setPrepayId(prepayId);
        payOrder.setAmount(amount);
        payOrder.setPayType(payType);
        payOrder.setStatus(status);
        return payOrder;
    }
    public static PayOrder toPayOrder(String orderNo,String status,String transactionId,String completeTime,String paySubtype) {
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderNo(orderNo);
        payOrder.setStatus(status);
        payOrder.setTransactionId(transactionId);
        payOrder.setCompleteTime(completeTime);
        payOrder.setPaySubtype(paySubtype);
        return payOrder;
    }

    public static List<PayOrderListPo> toPayOrderListPos(List<PayOrder> payOrders, Map<String, SystemDict> systemDictMap) {
        if (payOrders != null) {
            List<PayOrderListPo> payOrderListPos = new ArrayList<>();
            for (PayOrder payOrder :payOrders) {
                PayOrderListPo payOrderListPo =toPayOrderListPo(payOrder,systemDictMap);
                payOrderListPos.add(payOrderListPo);
            }
            return payOrderListPos;
        }
        return null;
    }

    private static PayOrderListPo toPayOrderListPo(PayOrder payOrder,Map<String, SystemDict> systemDictMap){
        if (payOrder != null) {
            PayOrderListPo payOrderListPo = new PayOrderListPo();
            payOrderListPo.setAccountId(payOrder.getAccountId());
            payOrderListPo.setPrepayId(payOrder.getPrepayId());
            payOrderListPo.setOrderNo(payOrder.getOrderNo());
            payOrderListPo.setAmount(payOrder.getAmount());
            payOrderListPo.setPayType(payOrder.getPayType());
            payOrderListPo.setOrderTime(payOrder.getOrderTime());
            payOrderListPo.setCompleteTime(payOrder.getCompleteTime()==null?null:DateUtil.getDateDetailTime(payOrder.getCompleteTime()));
            payOrderListPo.setStatus(systemDictMap.get(payOrder.getStatus())==null?"":systemDictMap.get(payOrder.getStatus()).getDisplayName());
            payOrderListPo.setTransactionId(payOrder.getTransactionId());
            return payOrderListPo;
        }
        return null;
    }
}
