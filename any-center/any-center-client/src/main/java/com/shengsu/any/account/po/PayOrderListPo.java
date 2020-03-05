package com.shengsu.any.account.po;

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
    private String orderNo;
    private BigDecimal amount;
    private String payType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;// 下单时间
    private String completeTime;// 订单完成时间

}
