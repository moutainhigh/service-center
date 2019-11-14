package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * 案件人员
 * @author zxh
 *
 */
@Data
public class LawcasePerson extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String personId;
	private String caseId;
	private String personName;
	private String personType;
    private String litigantType;
    private String isBaile;
    private String departmentName;
    private String creditCode;
    private String litigantIdentity;
    private String legalRepresentative;

}
