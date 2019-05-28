package com.shengsu.lawcase.entity;

import java.util.ArrayList;
import java.util.List;

import com.shengsu.base.entity.BaseEntity;

/**
 * 案件阶段
 * @author zxh
 *
 */
public class LawcasePhase extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String phaseId; 
	private String caseId; 
	private String phaseName;
    private String delFlag;
    private int sort;
    private Short isCustomerVisible;//是否可见
    private List<LawcasePhaseTask> lawcasePhaseTasks = new ArrayList<LawcasePhaseTask>();//阶段任务
	public String getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
	public String getPhaseName() {
		return phaseName;
	}
	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public List<LawcasePhaseTask> getLawcasePhaseTasks() {
		return lawcasePhaseTasks;
	}
	public void setLawcasePhaseTasks(List<LawcasePhaseTask> lawcasePhaseTasks) {
		this.lawcasePhaseTasks = lawcasePhaseTasks;
	}
	public Short getIsCustomerVisible() {
		return isCustomerVisible;
	}
	public void setIsCustomerVisible(Short isCustomerVisible) {
		this.isCustomerVisible = isCustomerVisible;
	}
	
}
