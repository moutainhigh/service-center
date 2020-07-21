package com.shengsu.website.market.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 14:07
 **/
@Data
public class LawDocQueryPo implements Serializable {
    private String docType;
    private String docSubtype;
    private String docSubtypeStr;
    private String docName;
    private String fullName;
    private String ossResourceId;
    private String ossResourceUrl;
    private String downloads;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;
}
