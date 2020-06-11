package com.shengsu.website.market.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-06-11 13:51
 **/
@Data
public class AlipayDecrypt implements Serializable {
    private String mobile;
    private String msg;
    private String code;
}
