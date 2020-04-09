package com.shengsu.trade.pay.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-09 11:51
 **/
@Data
public class BdBizInfo implements Serializable {
    private String appKey;
    private String dealld;
    private String tpOrderId;
    private String totalAmount;
}
