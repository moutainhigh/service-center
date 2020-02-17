package com.shengsu.any.account.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-16 14:28
 **/
@Data
public class AccountListPo implements Serializable {
    private String userId;
    private String tel;// 手机号
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private BigDecimal income;// 收入
    private BigDecimal expend;// 支出
    private BigDecimal accountBalance;
}
