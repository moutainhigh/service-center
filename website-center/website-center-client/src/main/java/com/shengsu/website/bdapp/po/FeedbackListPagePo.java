package com.shengsu.website.bdapp.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 14:31
 **/
@Data
public class FeedbackListPagePo implements Serializable {
    private String feedbackId;
    private String suggestion;
    private String tel;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
