package com.shengsu.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

@Data
public class User extends BaseEntity {
	private static final long serialVersionUID = 6253394804472191324L;

	private String userId;
	private String userName;
	private String realName;
	private Short userType;
	private String pwd;
	private String descri;
	private String creator;
	private String mobile;
	private Short gender;
	private String tel;
	private String email;
	private String iconOssResourceId;
	private String org;
}
