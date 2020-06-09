package com.shengsu.website.market.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 13:20
 **/
@Data
public class Lawyer extends BaseEntity {
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
    private String iconOssResourceId;

    /**
     * 擅长领域
     */
    private String field;
    private Integer consultTimes;
    private Integer praiseTimes;

}
