package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

@Data
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

}
