package com.shengsu.website.market.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-23 20:14
 **/
@Data
public class MiniprogramData implements Serializable {
    //小程序appid
    private String appid;
    // 网页路径
    private String pagepath;
}
