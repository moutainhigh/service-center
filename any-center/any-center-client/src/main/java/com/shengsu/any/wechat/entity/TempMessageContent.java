package com.shengsu.any.wechat.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TempMessageContent implements Serializable {

    //消息内容
    private String value;

    //消息字体颜色
    private String color;

    public TempMessageContent(String value, String color) {
        this.value = value;
        this.color = color;
    }
}