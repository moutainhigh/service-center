package com.shengsu.website.market.po;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-13 13:52
 **/
@Data
public class LawyerPo extends BaseEntity {
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
    private Integer consultTimes;
    private Integer praiseTimes;
    private String consultFee;
    private BigDecimal price;
    private String rank;
}
