package com.shengsu.website.market.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-13 17:58
 **/
@Data
public class LawDocCreateVo implements Serializable {
    private String docType;
    private String docName;// 文档名称
    private String fullName;// 文档全称
    private String ossResourceId;// 资源id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;// 上传时间

}
