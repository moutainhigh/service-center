package com.shengsu.any.account.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-18 14:53
 **/
@Data
public class BalanceChangeRecordPo implements Serializable {
    private BigDecimal beforeBalance;
    private BigDecimal afterBalance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String creator;
    private String actionType;
    private String remark;
}
