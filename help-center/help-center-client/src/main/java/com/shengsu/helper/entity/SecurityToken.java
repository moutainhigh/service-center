package com.shengsu.helper.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 临时token对象
 */
@Data
public class SecurityToken implements Serializable{
    private String accessKeyId ;
    private String accessKeySecret;
    private String stsToken;
    private String bucket;
}
