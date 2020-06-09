package com.shengsu.website.market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-10 17:45
 **/
@Data
public class LawKnowledge extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String knowledgeId;
    private String firstCategoryId;
    private String secondCategoryId;
    private String thirdCategoryId;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dateTime;
    private String source;
    private String pv;
    private String pictureOssId;

}
