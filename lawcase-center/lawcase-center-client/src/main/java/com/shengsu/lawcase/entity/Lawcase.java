package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * 案件
 * @author zxh
 *
 */
public class Lawcase extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private String caseId;
	private String userId;
	private String caseCode;
	private String caseName;
	private String caseCause;
	private String caseType;
	private String target;
	private String counselFee;
	private String isOpenCourt;
	private String isBidCase;
	private String filed;
	private String approveStatus;
	private String addressProvince;
	private String payAgreement;
	private String hearingProcedure;
	private List<LawcasePerson> litigants = new ArrayList<LawcasePerson>();//当事人
	private List<LawcasePerson> hearers=new ArrayList<LawcasePerson>();//审理人员
	private List<LawcasePerson> assistPersons=new ArrayList<LawcasePerson>();//辅助人员
	private String responsibleUserId;// 案件负责人用户Id
	private LawcaseUser responsiblePerson;//负责人
	private String creatorUserId;//案件创建者用户Id
	private String hearingOrgan;//审理机构
	private String hearingOrganCode;//审理机构案号
	private String officeCode;//所内案号
	private List<LawcasePhase> lawcasePhases = new ArrayList<LawcasePhase>();//案件阶段
	private String memo;//备注
	private String rank;//案件等级
	private String delFlag;//删除标识
	private List<LawcasePhaseTask> lawcasePhaseTasks = new ArrayList<LawcasePhaseTask>();//任务

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseCause() {
		return caseCause;
	}

	public void setCaseCause(String caseCause) {
		this.caseCause = caseCause;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCounselFee() {
		return counselFee;
	}

	public void setCounselFee(String counselFee) {
		this.counselFee = counselFee;
	}

	public String getIsOpenCourt() {
		return isOpenCourt;
	}

	public void setIsOpenCourt(String isOpenCourt) {
		this.isOpenCourt = isOpenCourt;
	}

	public String getIsBidCase() {
		return isBidCase;
	}

	public void setIsBidCase(String isBidCase) {
		this.isBidCase = isBidCase;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}

	public String getPayAgreement() {
		return payAgreement;
	}

	public void setPayAgreement(String payAgreement) {
		this.payAgreement = payAgreement;
	}

	public String getHearingProcedure() {
		return hearingProcedure;
	}

	public void setHearingProcedure(String hearingProcedure) {
		this.hearingProcedure = hearingProcedure;
	}

	public List<LawcasePerson> getLitigants() {
		return litigants;
	}

	public void setLitigants(List<LawcasePerson> litigants) {
		this.litigants = litigants;
	}

	public List<LawcasePerson> getHearers() {
		return hearers;
	}

	public void setHearers(List<LawcasePerson> hearers) {
		this.hearers = hearers;
	}

	public List<LawcasePerson> getAssistPersons() {
		return assistPersons;
	}

	public void setAssistPersons(List<LawcasePerson> assistPersons) {
		this.assistPersons = assistPersons;
	}

	public String getResponsibleUserId() {
		return responsibleUserId;
	}

	public void setResponsibleUserId(String responsibleUserId) {
		this.responsibleUserId = responsibleUserId;
	}

	public LawcaseUser getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(LawcaseUser responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(String creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	public String getHearingOrgan() {
		return hearingOrgan;
	}

	public void setHearingOrgan(String hearingOrgan) {
		this.hearingOrgan = hearingOrgan;
	}

	public String getHearingOrganCode() {
		return hearingOrganCode;
	}

	public void setHearingOrganCode(String hearingOrganCode) {
		this.hearingOrganCode = hearingOrganCode;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public List<LawcasePhase> getLawcasePhases() {
		return lawcasePhases;
	}

	public void setLawcasePhases(List<LawcasePhase> lawcasePhases) {
		this.lawcasePhases = lawcasePhases;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public List<LawcasePhaseTask> getLawcasePhaseTasks() {
		return lawcasePhaseTasks;
	}

	public void setLawcasePhaseTasks(List<LawcasePhaseTask> lawcasePhaseTasks) {
		this.lawcasePhaseTasks = lawcasePhaseTasks;
	}
}
