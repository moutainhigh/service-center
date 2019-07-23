package com.shengsu.consult.entity;

import com.shengsu.base.entity.BaseEntity;

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
	public String getConsultId() {
		return consultId;
	}
	public void setConsultId(String consultId) {
		this.consultId = consultId;
	}
	public String getConsultContent() {
		return consultContent;
	}
	public void setConsultContent(String consultContent) {
		this.consultContent = consultContent;
	}
	public String getConsultor() {
		return consultor;
	}
	public void setConsultor(String consultor) {
		this.consultor = consultor;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getLawyer() {
		return lawyer;
	}
	public void setLawyer(String lawyer) {
		this.lawyer = lawyer;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getConsultType() {
		return consultType;
	}
	public void setConsultType(String consultType) {
		this.consultType = consultType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

}
