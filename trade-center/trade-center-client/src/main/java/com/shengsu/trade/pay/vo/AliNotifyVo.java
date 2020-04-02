package com.shengsu.trade.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AliNotifyVo implements Serializable {
    // 内部支付订单号
    String orderNo;
}
