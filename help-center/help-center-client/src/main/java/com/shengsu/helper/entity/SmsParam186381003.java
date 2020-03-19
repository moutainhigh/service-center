package com.shengsu.helper.entity;

import lombok.Data;

/**
 * 发送给律师
 */
@Data
public class SmsParam186381003 {
    String lawyer;
    String clueType;

    public SmsParam186381003(String lawyer, String clueType) {
        this.lawyer = lawyer;
        this.clueType = clueType;
    }

}
