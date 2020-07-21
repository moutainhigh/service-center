package com.shengsu.website.market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
@Data
public class BuyRecord extends BaseEntity {
    private String recordId;

    private String wechatOpenid;

    private String orderNo;

    private String buyType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date buyTime;

    private Date expireTime;

    private Date createTime;

    private Date modifyTime;
}