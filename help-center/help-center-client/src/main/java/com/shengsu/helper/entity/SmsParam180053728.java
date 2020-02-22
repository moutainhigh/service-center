package com.shengsu.helper.entity;

import lombok.Data;

/**
 * 验证码
 */
@Data
public class SmsParam180053728 {
    String code;

    public SmsParam180053728(String code) {
        this.code = code;
    }
}
