package com.shengsu.bench.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * Created by zyc on 2019/8/7.
 */
@Data
public class BenchJournalism extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date journalismDate;
    private String url;
    private String pictureOssId;
    private Integer isTop;
    private Integer delFlag;
    private String features;
    private String pictureOssUrl;


}
