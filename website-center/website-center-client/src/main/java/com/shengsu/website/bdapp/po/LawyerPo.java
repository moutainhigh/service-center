package com.shengsu.website.bdapp.po;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

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
}
