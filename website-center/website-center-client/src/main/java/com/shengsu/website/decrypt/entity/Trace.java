package com.shengsu.website.decrypt.entity;

import lombok.Data;

/**
 * @program: yuanshou-website-platform
 * @author: Bell
 * @create: 2020-04-08 18:17
 **/
@Data
public class Trace {
    private String text;
    private String sessionKey;
    private String origin;
    private String source;
}
