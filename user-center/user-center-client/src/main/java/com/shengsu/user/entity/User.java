package com.shengsu.user.entity;

import com.shengsu.base.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class User extends BaseEntity {
	private int id;
	private String username;
	private int age;
	private Date ctm;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getCtm() {
		return ctm;
	}
	public void setCtm(Date ctm) {
		this.ctm = ctm;
	}
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
