package com.shengsu.any.wechat.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-03-26 10:27
 **/
@Data
public class TempMessageData implements Serializable {
    private String openId;
    private TempMessageParamData data;
}
