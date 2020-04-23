package com.shengsu.website.bdapp.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-13 11:19
 **/
@Data
public class LawKnowledgeSimplePo implements Serializable {
    private String knowledgeId;
    private String title;
    private String thirdCategoryName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dateTime;
    private String pictureOssUrl;// 图片资源url
}
