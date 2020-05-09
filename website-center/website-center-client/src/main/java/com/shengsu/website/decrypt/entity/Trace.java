package com.shengsu.website.decrypt.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yuanshou-website-platform
 * @author: Bell
 * @create: 2020-04-08 18:17
 **/
@Data
public class Trace implements Serializable {
    private String text;
    private String sessionKey;
    private String origin;
    private String source;
}
