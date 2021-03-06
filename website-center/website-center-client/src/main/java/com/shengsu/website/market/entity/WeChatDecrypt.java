package com.shengsu.website.market.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-09 17:51
 **/
@Data
public class WeChatDecrypt implements Serializable {
    private String sessionKey;
    private String encryptedData;
    private String iv;
    private String origin;
    private String source;
}
