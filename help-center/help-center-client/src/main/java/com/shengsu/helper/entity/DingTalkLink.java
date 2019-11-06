package com.shengsu.helper.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zyc on 2019/11/5.
 */
@Data
public class DingTalkLink implements Serializable{

    private String dingGroupUrl;
    private String title;
    private String text;
    private String msgUrl;
    private String picUrl;
}
