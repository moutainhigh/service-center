package com.shengsu.website.market.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-13 15:07
 **/
@Data
public class LawKnowledgeCurrentPo implements Serializable {
    private String knowledgeId;
    private String firstCategoryId;
    private String secondCategoryId;
    private String thirdCategoryId;
    private String firstCategoryName;
    private String secondCategoryName;
    private String thirdCategoryName;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dateTime;
    private String source;
    private String pv;
    private String pictureOssId;
    private String pictureOssUrl;// 图片资源url
    private String firstCategoryUrl;//一级类目下的链接url
}
