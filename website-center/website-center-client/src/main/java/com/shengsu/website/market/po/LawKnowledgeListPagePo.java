package com.shengsu.website.market.po;

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
public class LawKnowledgeListPagePo implements Serializable {
    private String knowledgeId;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date dateTime;
}
