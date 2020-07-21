package com.shengsu.website.market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-13 16:45
 **/
@Data
public class LawDoc extends BaseEntity {
    private String docId;// 文档id
    private String docType;// 文档类型
    private String docSubtype; // 文档子类型
    private String docName;// 文档名称
    private String fullName;// 文档全称
    private String ossResourceId;// 资源id
    private String downloads;// 下载次数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;// 上传时间
}
