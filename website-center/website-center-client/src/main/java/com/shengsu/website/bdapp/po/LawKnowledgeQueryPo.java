package com.shengsu.website.bdapp.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 14:07
 **/
@Data
public class LawKnowledgeQueryPo implements Serializable {
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
    private String pv;
}
