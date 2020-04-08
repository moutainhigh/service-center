package com.shengsu.website.home.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import com.shengsu.util.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class SuccessCase extends BaseEntity{

    private Long id;
    private String legendOssId;
    private Integer type;
    private String title;
    @JsonFormat(pattern = DateUtil.DEFAULT_DATE_FORMAT,timezone = "GMT+8")
    private Date caseDate;
    private String summary;
    private String source;
    private String author;
    private String content;
    private Integer isHomeShow;
    private String relevant;
}
