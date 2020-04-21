package com.shengsu.website.bdapp.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-16 13:46
 **/
@Data
public class ComplaintListPagePo implements Serializable {
    private String complaintId;
    private String url;
    private String name;
    private String tel;
    private String appeal;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
