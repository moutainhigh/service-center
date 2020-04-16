package com.shengsu.website.bdapp.po;

import lombok.Data;

import java.io.Serializable;

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
}
