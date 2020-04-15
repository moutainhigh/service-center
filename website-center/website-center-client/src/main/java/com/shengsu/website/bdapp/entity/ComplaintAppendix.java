package com.shengsu.website.bdapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 17:13
 **/
@Data
public class ComplaintAppendix extends BaseEntity {
    private String appendixId;
    private String complaintId;
    private String appendixName;
    private String fullName;
    private String ossResourceId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;
    private String idCardFlag;
    private String ortFlag;
    private String enterpriseFlag;
    private String trademarkFlag;
}
