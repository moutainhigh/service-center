package com.shengsu.website.bdapp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 13:39
 **/
@Data
public class FeedbackCreateVo implements Serializable{
    private String suggestion;
    private String tel;
}
