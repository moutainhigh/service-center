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
public class AccountRecordDetailsPo implements Serializable {
    private String tel;// 手机号
    private BigDecimal amount;// 金额
    private String source;// 来源
    private String actionType;// 动作类型
    private String inOrOutType;// 收支类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String realName;
}
