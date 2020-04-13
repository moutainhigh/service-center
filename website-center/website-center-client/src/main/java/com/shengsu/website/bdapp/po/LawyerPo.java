package com.shengsu.website.bdapp.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-13 13:52
 **/
@Data
public class LawyerPo implements Serializable {
    /**
     * 用户id
     */
    private String lawyerId;

    /**
     * 真实姓名
     */
    private String lawyerName;

    /**
     * 头像资源
     */
    private String iconOssResourceUrl;

    /**
     * 擅长领域
     */
    private String field;
}
