package com.shengsu.any.wechat.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TemplateMessage implements Serializable {
    //用户openid
    private String touser;
    //模板消息ID
    private String template_id;
    //详情跳转页面
    private String url;
    //模板数据封装实体
    private TempMessageParamData data;


}