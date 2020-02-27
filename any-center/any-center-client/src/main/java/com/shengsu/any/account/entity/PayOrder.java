package com.shengsu.any.account.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

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
    private String status;
}
