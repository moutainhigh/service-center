package com.shengsu.any.account.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-18 13:32
 **/
@Data
public class BalanceChangeVo implements Serializable {
    private String userId;
    private BigDecimal amount;
    private String actionType;
    private String creator;
    private String remark;
}
