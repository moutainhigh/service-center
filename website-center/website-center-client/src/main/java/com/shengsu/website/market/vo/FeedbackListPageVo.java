package com.shengsu.website.market.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 14:00
 **/
@Data
public class FeedbackListPageVo extends BaseEntity {
    private String suggestion;
    private String tel;
    private String startTime;
    private String endTime;
}
