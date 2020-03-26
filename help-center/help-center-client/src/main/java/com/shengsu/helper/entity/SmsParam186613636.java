package com.shengsu.helper.entity;

import lombok.Data;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-03-26 09:16
 **/
@Data
public class SmsParam186613636 {
    String lawyer;
    String clueType;

    public SmsParam186613636(String lawyer, String clueType) {
        this.lawyer = lawyer;
        this.clueType = clueType;
    }
}
