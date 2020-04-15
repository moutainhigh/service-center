package com.shengsu.website.bdapp.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 11:36
 **/
@Data
public class Feedback extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String feedbackId;
    private String suggestion;
    private String tel;
}
