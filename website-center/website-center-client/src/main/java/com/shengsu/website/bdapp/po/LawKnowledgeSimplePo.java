package com.shengsu.website.bdapp.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-13 11:19
 **/
@Data
public class LawKnowledgeSimplePo implements Serializable {
    private String knowledgeId;
    private String title;
    private String pictureOssId;
    private String pictureOssUrl;// 图片资源url
}