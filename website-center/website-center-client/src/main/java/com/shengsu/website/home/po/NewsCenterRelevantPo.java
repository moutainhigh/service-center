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
public class NewsCenterRelevantPo implements Serializable {

    private Long id;
    private String title;
    @JsonFormat(pattern = DateUtil.DEFAULT_DATE_FORMAT, timezone = "GMT+8")
    private Date newsDate;
}
