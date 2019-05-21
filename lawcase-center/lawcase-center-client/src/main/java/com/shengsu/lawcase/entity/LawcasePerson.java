package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;

/**
 * 案件人员
 * @author zxh
 *
 */
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

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getLitigantType() {
		return litigantType;
	}

	public void setLitigantType(String litigantType) {
		this.litigantType = litigantType;
	}

	public String getIsBaile() {
		return isBaile;
	}

	public void setIsBaile(String isBaile) {
		this.isBaile = isBaile;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getLitigantIdentity() {
		return litigantIdentity;
	}

	public void setLitigantIdentity(String litigantIdentity) {
		this.litigantIdentity = litigantIdentity;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
}
