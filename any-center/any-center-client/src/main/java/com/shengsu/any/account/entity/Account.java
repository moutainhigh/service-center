package com.shengsu.any.account.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 个人账户实体
 * @author: lipiao
 * @create: 2020-01-08 09:42
 **/
@Data
public class Account extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String accountId;// 账户id
    private String userId;// 用户id
    private BigDecimal balance;// 余额
}
