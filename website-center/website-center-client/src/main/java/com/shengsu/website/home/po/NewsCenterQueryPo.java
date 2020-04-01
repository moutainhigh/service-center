package com.shengsu.website.home.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.util.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class NewsCenterQueryPo implements Serializable {

    private Long id;
    private Integer type;
    private String title;
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT, timezone = "GMT+8")
    private Date newsDate;
    private String summary;
    private String source;
    private String author;
    private String content;
    private Integer isHomeShow;
    private List<NewsCenterRelevantPo> relevantNewCenterList;
    private String pictureOssId;// 新闻图片资源id
    private String pictureOssUrl;// 新闻图片资源url
    private String ascription;// 新闻归属
}
