package com.shengsu.helper.entity;

import lombok.Data;

/**
 * 临时token对象
 */
@Data
public class SecurityToken {
    private String accessKeyId ;
    private String accessKeySecret;
    private String stsToken;
    private String bucket;
}
