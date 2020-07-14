package com.shengsu.website.market.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 11:49
 **/
@Data
public class LawDocUpdateVo implements Serializable{
    @NotBlank
    private String docId;
    private String docType;
    private String docName;// 文档名称
    private String fullName;// 文档全称
    private String ossResourceId;// 资源id
    private String downloads;// 下载次数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;// 上传时间
}
