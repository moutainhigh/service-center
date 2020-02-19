package com.shengsu.any.account.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:12
 **/
@Data
public class AccountRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String clueId;// 线索id;
    private String recordId;// 记录id
    private String userId;// 用户id
    private BigDecimal amount;// 金额
    private String source;// 来源
    private BigDecimal beforeBalance;// 变更前余额
    private BigDecimal afterBalance;// 变更后余额
    private String actionType;// 动作类型
    private String creator;// 创建者
    private String remark;// 备注
}
