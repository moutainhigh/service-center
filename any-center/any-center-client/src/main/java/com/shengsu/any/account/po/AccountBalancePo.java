package com.shengsu.any.account.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-28 10:26
 **/
@Data
public class AccountBalancePo {
    private String accountId;// 账户id
    private String userId;// 用户id
    private BigDecimal balance;// 余额
}
