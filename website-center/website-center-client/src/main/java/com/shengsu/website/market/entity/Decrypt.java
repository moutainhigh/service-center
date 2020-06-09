package com.shengsu.website.market.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yuanshou-website-platform
 * @author: Bell
 * @create: 2020-04-08 15:38
 **/
@Data
public class Decrypt implements Serializable {
    private String text;
    private String sessionKey;
}
