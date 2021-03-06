package com.shengsu.website.market.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-16 11:54
 **/
@Data
public class ComplaintListPageVo extends BaseEntity {
    private String name;
    private String tel;
    private String complaintType;
    private String startTime;
    private String endTime;
}
