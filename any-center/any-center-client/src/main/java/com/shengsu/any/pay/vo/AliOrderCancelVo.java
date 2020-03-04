package com.shengsu.any.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AliOrderCancelVo implements Serializable {
    // 内部支付订单号
    String orderNo;

    //支付宝预支付订单号
    String prepayId;
}
