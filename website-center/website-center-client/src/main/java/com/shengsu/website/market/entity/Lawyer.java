package com.shengsu.website.market.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

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
    private BigDecimal consultFee;
    private String rank;
    private String introduction;
    private String overallEvaluation;
    private String professionalDegree;
    private String serviceEfficiency;
    private String serviceAttitude;
    private String systemTag;
}
