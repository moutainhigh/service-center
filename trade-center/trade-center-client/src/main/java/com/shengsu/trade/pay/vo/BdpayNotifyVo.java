package com.shengsu.trade.pay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-09 16:58
 **/
@Data
public class BdpayNotifyVo implements Serializable {
    private String tpOrderId;//订单号
    private String status;// 订单支付状态
    private String orderId;// 订单ID
    private String userId;// 用户ID
    private Integer payTime;//支付完成时间
    private String rsaSign;
}
