package com.shengsu.trade.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxOrderCancelVo implements Serializable {
    // 外部订单号(商户号)
    String orderNo;
}
