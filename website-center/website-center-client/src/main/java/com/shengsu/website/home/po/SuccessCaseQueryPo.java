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
public class SuccessCaseQueryPo implements Serializable {

    private Long id;
    private Integer type;
    private String title;
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT, timezone = "GMT+8")
    private Date caseDate;
    private String summary;
    private String source;
    private String author;
    private String content;
    private String legendOssId;
    private String legendOssUrl;
    private Integer isHomeShow;
    private List<SuccessCaseRelevantPo> relevantNewCenterList;
}
