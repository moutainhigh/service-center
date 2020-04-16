package com.shengsu.trade.pay.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zyc on 2019/8/26.
 */
@Data
public class PayOrderListPo implements Serializable{
    private String accountId;
    private String prepayId;
    private String orderNo;
    private BigDecimal amount;
    private String payType;
    private String paySubtype;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;// 下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date completeTime;// 订单完成时间
    private String status;
    private String transactionId;// 微信支付订单号


}
