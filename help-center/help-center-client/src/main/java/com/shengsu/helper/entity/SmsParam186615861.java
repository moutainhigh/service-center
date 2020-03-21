package com.shengsu.helper.entity;

import lombok.Data;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-03-21 11:32
 **/
@Data
public class SmsParam186615861 {
    String client;
    String lawyer;
    String tel;
    public SmsParam186615861(String client, String lawyer, String tel) {
        this.client = client;
        this.lawyer = lawyer;
        this.tel = tel;
    }
}
