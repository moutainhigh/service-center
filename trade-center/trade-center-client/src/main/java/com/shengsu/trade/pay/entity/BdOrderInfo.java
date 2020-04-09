package com.shengsu.trade.pay.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-09 11:52
 **/
@Data
public class BdOrderInfo implements Serializable {
    private String dealId;
    private String appKey;
    private String totalAmount;
    private String tpOrderId;
    private String dealTitle;
    private String signFieldsRange;
    private String rsaSign;
    private String bizInfo;
    public void setBdBizInfo(BdBizInfo bizInfo) {
        String json = JSON.toJSONString(bizInfo);
        StringBuffer buffer = new StringBuffer("{\"tpData\":");
        buffer.append(json);
        buffer.append("}");
        this.setBizInfo(buffer.toString());
    }
}
