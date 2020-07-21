package com.shengsu.website.market.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-21 17:25
 **/
@Data
public class BuyRecordPo implements Serializable {
    private String recordId;

    private String wechatOpenid;

    private String orderNo;

    private String buyType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date buyTime;
    private String isExpired;
}
