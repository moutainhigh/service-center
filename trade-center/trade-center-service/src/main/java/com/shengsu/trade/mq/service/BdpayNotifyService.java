package com.shengsu.trade.mq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.mq.MessageProcessor;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.service.BdpayService;
import com.shengsu.trade.pay.service.PayOrderService;
import com.shengsu.trade.pay.util.PayOrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.shengsu.trade.app.constant.BizConst.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-03-26 10:40
 **/
@Slf4j
@Service("bdpayNotifyService")
public class BdpayNotifyService implements MessageProcessor<JSONObject> {
    @Autowired
    private BdpayService bdpayService;
    @Autowired
    private PayOrderService payOrderService;
    @Override
    public boolean handleMessage(JSONObject jsonObject) {
        log.info("处理消息："+ jsonObject);
        // 订单号
        String outTradeNo =jsonObject.getString("outTradeNo");
        // 状态
        String status = jsonObject.getString("status");
        // 订单ID
        String orderId = jsonObject.getString("orderId") ;
        // 金额
        String totalMoney =jsonObject.getString("totalMoney");
        // 支付完成时间
        String payTime =jsonObject.getString("payTime");
        // 支付渠道
        String paySubtype = jsonObject.getString("paySubtype") ;
        // 支付渠道转化
        paySubtype = bdpayService.formatPaySubtype(paySubtype);
        // 用户id
        String siteId = jsonObject.getString("siteId") ;
        // 校验百度参数返回值
        if (BAIDU_ORDER_STATUS_PAID.equals(status)){
            log.info("支付状态=2: 支付成功");
            // 查询订单状态是否已经支付--如果已经支付不再提醒,百度支付需校验 tpOrderId(outTradeNo)商户号以及金额是实际金额
            PayOrder order = payOrderService.getByOrderNo(outTradeNo);
            log.info(JSON.toJSONString(order));
            BigDecimal amount = new BigDecimal(100).multiply(order.getAmount()).setScale(0);
            if (null !=order && ORDER_STATUS_UNPAID.equals(order.getStatus())&& totalMoney.equals(amount.toString())){
                log.info(JSON.toJSONString("修改订单状态"));
                // 修改订单-包括订单状态
                PayOrder payOrder = PayOrderUtils.toPayOrder(outTradeNo,ORDER_STATUS_PAID,orderId,payTime,paySubtype,siteId);
                payOrderService.updateOrder(payOrder);
            }
        }else{
            log.error("支付失败");
        }
        return true;
    }
    @Override
    public Class<JSONObject> getClazz() {
        return JSONObject.class;
    }
}
