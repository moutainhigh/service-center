package com.shengsu.website.home.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LawcaseConsult extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String consultId;
    private String consultContent;
    private String origin;
    private String consultor;
    private String contact;
    private String target;
    private String reply;
    private String lawyer;
    private String source;
    private String redirectUrl;
    private String startTime;
	private String endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;//创建时间
    private List<LawcaseConsultAppendix> appendixList;
    private String lawField;//法律领域
    private String enterpriseName;
    private String answer;
    private String score;
}
