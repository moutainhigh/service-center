package com.shengsu.helper.entity;

import lombok.Data;

/**
 * 验证码
 */
@Data
public class SmsParam184105294 {
    String lawyer;
    String clueType;

    public SmsParam184105294(String lawyer, String clueType) {
        this.lawyer = lawyer;
        this.clueType = clueType;
    }
}
