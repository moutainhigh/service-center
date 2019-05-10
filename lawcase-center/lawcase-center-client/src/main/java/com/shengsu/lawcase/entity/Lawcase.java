package com.shengsu.lawcase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;

import java.util.Date;

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
//	private List<LawcasePerson> litigants = new ArrayList<LawcasePerson>();//当事人
//	private List<LawcasePerson> hearers=new ArrayList<LawcasePerson>();//审理人员
//	private List<LawcasePerson> assistPersons=new ArrayList<LawcasePerson>();//辅助人员
	private String responsibleUserId;// 案件负责人用户Id
//	private User responsiblePerson;//负责人
	private String creatorUserId;//案件创建者用户Id
//	private User creator;//案件创建人
//	private List<User> approvers = new ArrayList<User>();//审批人
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date stickTime;//置顶时间
	private String groupCount;//案件组数目 
	private String totalHours;//总计工时
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String stickState;//置顶状态;
	private String hearingOrgan;//审理机构
	private String hearingOrganCode;//审理机构案号
	private String officeCode;//所内案号
//	private List<LawcaseGroup> lawcaseGroups = new ArrayList<LawcaseGroup>();//案件组人员
	private String memo;
//	private List<LawcasePhase> lawcasePhases = new ArrayList<LawcasePhase>();//案件阶段

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

	public String getResponsibleUserId() {
		return responsibleUserId;
	}

	public void setResponsibleUserId(String responsibleUserId) {
		this.responsibleUserId = responsibleUserId;
	}

//	public User getResponsiblePerson() {
//		return responsiblePerson;
//	}
//
//	public void setResponsiblePerson(User responsiblePerson) {
//		this.responsiblePerson = responsiblePerson;
//	}

	public String getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(String creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

//	public User getCreator() {
//		return creator;
//	}
//
//	public void setCreator(User creator) {
//		this.creator = creator;
//	}

	public Date getStickTime() {
		return stickTime;
	}

	public void setStickTime(Date stickTime) {
		this.stickTime = stickTime;
	}

	public String getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(String groupCount) {
		this.groupCount = groupCount;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStickState() {
		return stickState;
	}

	public void setStickState(String stickState) {
		this.stickState = stickState;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
