package com.shengsu.website.bdapp.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 14:31
 **/
@Data
public class FeedbackListPagePo implements Serializable {
    private String feedbackId;
    private String suggestion;
    private String tel;
}
