package com.shengsu.website.market.entity;

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
    private String knowledgeId;
    private TempMessageParamData data;
}
