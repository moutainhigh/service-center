package com.shengsu.helper.entity;

import lombok.Data;

/**
 * 发送给客户
 */
@Data
public class SmsParam184115275 {
    String client;
    String lawyer;

    public SmsParam184115275(String client, String lawyer) {
        this.client = client;
        this.lawyer = lawyer;
    }
}
