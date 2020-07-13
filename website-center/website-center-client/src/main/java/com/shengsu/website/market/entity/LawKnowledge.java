package com.shengsu.website.market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-10 17:45
 **/
@Data
@Document(indexName = "law", type = "law_doc")
public class LawKnowledge extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    private String knowledgeId;
    private String firstCategoryId;
    private String secondCategoryId;
    private String thirdCategoryId;
    @Field
    private String title;
    @Field
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dateTime;
    private String source;
    private String pv;
    private String pictureOssId;
    private String creator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String createStartTime;// 创建开始时间
    private String createEndTime;// 创建结束时间

}
