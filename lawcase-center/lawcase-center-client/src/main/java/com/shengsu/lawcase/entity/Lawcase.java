package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 案件
 * @author zxh
 *
 */
@Data
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

}
