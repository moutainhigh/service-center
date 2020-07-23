package com.shengsu.website.market.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-09 16:48
 **/
@Data
public class Question extends BaseEntity {
    private String questionId;
    private String questionContent;
    private String systemTag;
}
