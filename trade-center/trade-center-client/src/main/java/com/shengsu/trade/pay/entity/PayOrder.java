package com.shengsu.trade.pay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-27 10:59
 **/
@Data
public class PayOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String accountId;
    private String orderNo;
    private String prepayId;
    private BigDecimal amount;
    private String payType;
    private String paySubtype;
    private String siteId;// 百度用户id
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;// 下单时间
    private String transactionId;// 微信支付订单号
    private String completeTime;// 订单完成时间
    private String startTime;
    private String endTime;

}
