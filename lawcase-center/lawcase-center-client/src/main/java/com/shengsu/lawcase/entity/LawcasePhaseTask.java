package com.shengsu.lawcase.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;

/**
 * 案件阶段任务
 * @author zxh
 *
 */
public class LawcasePhaseTask extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String taskId;
	private String phaseId; 
	private String caseId; 
	private String taskName;
    private String taskContent;
    private long taskTime;//工时
    private long totalHours;//总计工时
    private List<LawcaseTaskPerson> executors = new ArrayList<LawcaseTaskPerson>();//执行人
    private List<LawcaseUser> executorUser = new ArrayList<LawcaseUser>();
    private String taskHours;
	private String state;//任务状态
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date startTimeDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date endTimeDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date completeTime;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public long getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(long taskTime) {
		this.taskTime = taskTime;
	}

	public long getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(long totalHours) {
		this.totalHours = totalHours;
	}

	public List<LawcaseTaskPerson> getExecutors() {
		return executors;
	}

	public void setExecutors(List<LawcaseTaskPerson> executors) {
		this.executors = executors;
	}

	public List<LawcaseUser> getExecutorUser() {
		return executorUser;
	}

	public void setExecutorUser(List<LawcaseUser> executorUser) {
		this.executorUser = executorUser;
	}

	public String getTaskHours() {
		return taskHours;
	}

	public void setTaskHours(String taskHours) {
		this.taskHours = taskHours;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStartTimeDate() {
		return startTimeDate;
	}

	public void setStartTimeDate(Date startTimeDate) {
		this.startTimeDate = startTimeDate;
	}

	public Date getEndTimeDate() {
		return endTimeDate;
	}

	public void setEndTimeDate(Date endTimeDate) {
		this.endTimeDate = endTimeDate;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
}
