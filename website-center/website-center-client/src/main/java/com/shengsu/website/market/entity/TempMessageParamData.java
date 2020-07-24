package com.shengsu.website.market.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TempMessageParamData implements Serializable {
    private TempMessageContent first;
    private TempMessageContent keyword1;
    private TempMessageContent keyword2;
    private TempMessageContent remark;

}