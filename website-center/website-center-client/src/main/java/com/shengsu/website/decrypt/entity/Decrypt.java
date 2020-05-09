package com.shengsu.website.decrypt.entity;

import lombok.Data;

/**
 * @program: yuanshou-website-platform
 * @author: Bell
 * @create: 2020-04-08 15:38
 **/
@Data
public class Decrypt {
    private String text;
    private String sessionKey;
}
