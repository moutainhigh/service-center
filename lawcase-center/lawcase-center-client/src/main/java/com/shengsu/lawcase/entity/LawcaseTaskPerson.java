package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;

public class LawcaseTaskPerson extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskPersonId;
	private String taskId;
	private String userId;
	private String userType;
	private String iconUrl;
	private String caseId;
	private String phaseId;
	public String getTaskPersonId() {
		return taskPersonId;
	}
	public void setTaskPersonId(String taskPersonId) {
		this.taskPersonId = taskPersonId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
	
}
