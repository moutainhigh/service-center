package com.shengsu.website.home.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-05 09:14
 **/
@Data
public class LawcaseConsultAppendix extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String appendixId;
    private String refId;
    private String appendixName;
    private String ossResourceId;
    private double fileSize;
    private String fullName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadTime;
    private String appendixType;// 附件类型
}
