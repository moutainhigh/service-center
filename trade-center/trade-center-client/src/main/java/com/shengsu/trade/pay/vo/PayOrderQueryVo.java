package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-03-06 14:31
 **/
@Data
public class PayOrderQueryVo implements Serializable {
    // 商户订单号
    @NotBlank
    private String orderNo;
    // 支付类型
    @NotBlank
    private String payType;
}
