package com.shengsu.lawcase.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class DailyLawcaseStatisticsVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date startDate;//创建开始时间
	private Date endDate;//创建结束时间
	private String countDate;//统计日期
	private String countApproveStatusInit; //待审核
	private String countApproveStatusPass; //已通过待签约
	private String countApproveStatusAgent; //已签约代理中
	private String countApproveStatusReject; //已驳回
	private String countCivilAndCommercialLitigation; //民商事诉讼
	private String countCriminalProceeding; //刑事诉讼
	private String countForeignLitigation; //涉外诉讼
	private String countAdministrativeCase; //行政案件
	private String countContractDispute; //合同纠纷
	private String countOther; //其他诉讼
	private String countLawyerLetter; //律师函
	private String countLegalCounsel; //法律顾问
	private String countSpecialLegalService; //专项法律服务
	private String countNonLitigationCase; //非诉案件
	private String countTargetOfEntry; //进案标的
	private String countAgencyTarget; //代理标的
	private BigDecimal countApproveStatusCurrentDate; //审批状态当前日期
	private BigDecimal countApproveStatusLastYear; //审批状态去年
	private BigDecimal countApproveStatusLastWeek; //审批状态上周
	private String countApproveStatusYearOnYear;//审批状态同比
	private String countApproveStatusChainRatio;//审批状态环比
	private BigDecimal countTargetCurrentDate; //标的当前日期
	private BigDecimal countTargetLastYear; //标的去年
	private BigDecimal countTargetLastWeek; //标的上周
	private String countTargetYearOnYear;//标的同比
	private String countTargetChainRatio;//标的环比
	
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCountDate() {
		return countDate;
	}
	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}
	public String getCountApproveStatusInit() {
		if (null == countApproveStatusInit) {
			return "0";
		}
		return countApproveStatusInit;
	}
	public void setCountApproveStatusInit(String countApproveStatusInit) {
		this.countApproveStatusInit = countApproveStatusInit;
	}
	public String getCountApproveStatusPass() {
		if (null == countApproveStatusPass) {
			return "0";
		}
		return countApproveStatusPass;
	}
	public void setCountApproveStatusPass(String countApproveStatusPass) {
		this.countApproveStatusPass = countApproveStatusPass;
	}

	public String getCountApproveStatusAgent() {
		if (null == countApproveStatusAgent) {
			return "0";
		}
		return countApproveStatusAgent;
	}

	public void setCountApproveStatusAgent(String countApproveStatusAgent) {
		this.countApproveStatusAgent = countApproveStatusAgent;
	}

	public String getCountApproveStatusReject() {
		if (null == countApproveStatusReject) {
			return "0";
		}
		return countApproveStatusReject;
	}
	public void setCountApproveStatusReject(String countApproveStatusReject) {
		this.countApproveStatusReject = countApproveStatusReject;
	}
	
	public String getCountCivilAndCommercialLitigation() {
		if (null == countCivilAndCommercialLitigation) {
			return "0";
		}
		return countCivilAndCommercialLitigation;
	}
	public void setCountCivilAndCommercialLitigation(
			String countCivilAndCommercialLitigation) {
		this.countCivilAndCommercialLitigation = countCivilAndCommercialLitigation;
	}
	public String getCountCriminalProceeding() {
		if (null == countCriminalProceeding) {
			return "0";
		}
		return countCriminalProceeding;
	}
	public void setCountCriminalProceeding(String countCriminalProceeding) {
		this.countCriminalProceeding = countCriminalProceeding;
	}
	public String getCountForeignLitigation() {
		if (null == countForeignLitigation) {
			return "0";
		}
		return countForeignLitigation;
	}
	public void setCountForeignLitigation(String countForeignLitigation) {
		this.countForeignLitigation = countForeignLitigation;
	}
	public String getCountAdministrativeCase() {
		if (null == countAdministrativeCase) {
			return "0";
		}
		return countAdministrativeCase;
	}
	public void setCountAdministrativeCase(String countAdministrativeCase) {
		this.countAdministrativeCase = countAdministrativeCase;
	}
	public String getCountContractDispute() {
		if (null == countContractDispute) {
			return "0";
		}
		return countContractDispute;
	}
	public void setCountContractDispute(String countContractDispute) {
		this.countContractDispute = countContractDispute;
	}
	public String getCountOther() {
		if (null == countOther) {
			return "0";
		}
		return countOther;
	}
	public void setCountOther(String countOther) {
		this.countOther = countOther;
	}
	public String getCountLawyerLetter() {
		if (null == countLawyerLetter) {
			return "0";
		}
		return countLawyerLetter;
	}
	public void setCountLawyerLetter(String countLawyerLetter) {
		this.countLawyerLetter = countLawyerLetter;
	}
	public String getCountLegalCounsel() {
		if (null == countLegalCounsel) {
			return "0";
		}
		return countLegalCounsel;
	}
	public void setCountLegalCounsel(String countLegalCounsel) {
		this.countLegalCounsel = countLegalCounsel;
	}
	public String getCountSpecialLegalService() {
		if (null == countSpecialLegalService) {
			return "0";
		}
		return countSpecialLegalService;
	}
	public void setCountSpecialLegalService(String countSpecialLegalService) {
		this.countSpecialLegalService = countSpecialLegalService;
	}
	public String getCountNonLitigationCase() {
		if (null == countNonLitigationCase) {
			return "0";
		}
		return countNonLitigationCase;
	}
	public void setCountNonLitigationCase(String countNonLitigationCase) {
		this.countNonLitigationCase = countNonLitigationCase;
	}
	public String getCountTargetOfEntry() {
		if (null == countTargetOfEntry) {
			return "0";
		}
		return countTargetOfEntry;
	}
	public void setCountTargetOfEntry(String countTargetOfEntry) {
		this.countTargetOfEntry = countTargetOfEntry;
	}
	public String getCountAgencyTarget() {
		if (null == countAgencyTarget) {
			return "0";
		}
		return countAgencyTarget;
	}
	public void setCountAgencyTarget(String countAgencyTarget) {
		this.countAgencyTarget = countAgencyTarget;
	}
	public BigDecimal getCountApproveStatusCurrentDate() {
		return countApproveStatusCurrentDate;
	}
	public void setCountApproveStatusCurrentDate(
			BigDecimal countApproveStatusCurrentDate) {
		this.countApproveStatusCurrentDate = countApproveStatusCurrentDate;
	}
	public BigDecimal getCountApproveStatusLastYear() {
		return countApproveStatusLastYear;
	}
	public void setCountApproveStatusLastYear(BigDecimal countApproveStatusLastYear) {
		this.countApproveStatusLastYear = countApproveStatusLastYear;
	}
	public BigDecimal getCountApproveStatusLastWeek() {
		return countApproveStatusLastWeek;
	}
	public void setCountApproveStatusLastWeek(BigDecimal countApproveStatusLastWeek) {
		this.countApproveStatusLastWeek = countApproveStatusLastWeek;
	}
	public String getCountApproveStatusYearOnYear() {
		return countApproveStatusYearOnYear;
	}
	public void setCountApproveStatusYearOnYear(String countApproveStatusYearOnYear) {
		this.countApproveStatusYearOnYear = countApproveStatusYearOnYear;
	}
	public String getCountApproveStatusChainRatio() {
		return countApproveStatusChainRatio;
	}
	public void setCountApproveStatusChainRatio(String countApproveStatusChainRatio) {
		this.countApproveStatusChainRatio = countApproveStatusChainRatio;
	}
	public BigDecimal getCountTargetCurrentDate() {
		return countTargetCurrentDate;
	}
	public void setCountTargetCurrentDate(BigDecimal countTargetCurrentDate) {
		this.countTargetCurrentDate = countTargetCurrentDate;
	}
	public BigDecimal getCountTargetLastYear() {
		return countTargetLastYear;
	}
	public void setCountTargetLastYear(BigDecimal countTargetLastYear) {
		this.countTargetLastYear = countTargetLastYear;
	}
	public BigDecimal getCountTargetLastWeek() {
		return countTargetLastWeek;
	}
	public void setCountTargetLastWeek(BigDecimal countTargetLastWeek) {
		this.countTargetLastWeek = countTargetLastWeek;
	}
	public String getCountTargetYearOnYear() {
		return countTargetYearOnYear;
	}
	public void setCountTargetYearOnYear(String countTargetYearOnYear) {
		this.countTargetYearOnYear = countTargetYearOnYear;
	}
	public String getCountTargetChainRatio() {
		return countTargetChainRatio;
	}
	public void setCountTargetChainRatio(String countTargetChainRatio) {
		this.countTargetChainRatio = countTargetChainRatio;
	}
	
}
