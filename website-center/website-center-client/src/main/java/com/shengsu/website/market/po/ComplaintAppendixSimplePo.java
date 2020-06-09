package com.shengsu.website.market.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-16 14:45
 **/
@Data
public class ComplaintAppendixSimplePo implements Serializable {
    private String appendixName;
    private String fullName;
    private String ossResourceId;
    private String ossResourceUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;
}
