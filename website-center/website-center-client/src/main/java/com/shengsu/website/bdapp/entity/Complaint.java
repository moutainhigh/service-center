package com.shengsu.website.bdapp.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 16:57
 **/
@Data
public class Complaint extends BaseEntity {
    private String complaintId;
    private String url;
    private String name;
    private String tel;
    private String appeal;
    private String complaintType;
}
