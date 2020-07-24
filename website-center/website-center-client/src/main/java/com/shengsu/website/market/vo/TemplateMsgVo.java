package com.shengsu.website.market.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-23 16:09
 **/
@Data
public class TemplateMsgVo implements Serializable {
    private String knowledgeId;
    private String title;
    private String content;
    private String remark;
}
