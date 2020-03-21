package com.shengsu.helper.entity;

import lombok.Data;

/**
 * 发送给客户
 */
@Data
public class SmsParam184115275 {
    String client;
    String lawyer;
    String tel;
    public SmsParam184115275(String client, String lawyer,String tel) {
        this.client = client;
        this.lawyer = lawyer;
        this.tel = tel;
    }
}
