package com.shengsu.any.account.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-17 11:43
 **/
@Data
public class TotalExpendPo {
    private String userId;
    private BigDecimal totalExpend;
}
