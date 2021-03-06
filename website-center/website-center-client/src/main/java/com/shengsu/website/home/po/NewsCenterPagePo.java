package com.shengsu.website.home.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.util.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class NewsCenterPagePo implements Serializable {

    private Long id;
    private String title;
    private String summary;
    @JsonFormat(pattern = DateUtil.DEFAULT_DATE_FORMAT,timezone = "GMT+8")
    private Date newsDate;
    private String pictureOssId;
    private String pictureOssUrl;// 新闻图片资源url
    private String ascription;
}
