package com.shengsu.trade.pay.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-21 10:12
 **/
@Data
public class BdPayNotifyVo implements Serializable {
    // 订单号
    String outTradeNo;
    // 状态
    String status ;
    // 订单ID
    String orderId ;
    // 金额
    String totalMoney ;
    // 支付完成时间
    String payTime;
    // 支付渠道
    String paySubtype ;
    // 用户id
    String siteId ;
    // 结果
    Map<String,Object> consumedMap;
    // 返回结果
    Map<String,Object> resultMap;
}
