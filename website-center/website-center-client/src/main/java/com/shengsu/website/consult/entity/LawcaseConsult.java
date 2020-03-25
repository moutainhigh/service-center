package com.shengsu.website.consult.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

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
    private String startTime;
	private String endTime;
    private List<LawcaseConsultAppendix> appendixList;
}
