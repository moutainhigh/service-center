package com.shengsu.consult.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

@Data
public class Consult extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String consultId;
	private String consultContent;
	private String consultor;
	private String reply;
	private String lawyer;
	private String contact;
	private String delFlag;
	private String consultType;
	private String state;
    private String startTime;//创建开始时间
    private String endTime;//创建结束时间

}
