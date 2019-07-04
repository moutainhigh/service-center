package com.shengsu.system.entity;

import com.shengsu.base.entity.BaseEntity;

/**
 * @ClassName: SystemDictionary
 * @Description: 系统字典（实体类）
 * @author zxh
 * @date 2018-7-6
 * 
 */
public class SystemDictionary extends BaseEntity
{
	private static final long serialVersionUID = -1962148635528521842L;
	/**
	 * 字典id
	 */
	private String dictId;
	/**
	 * 字典编码
	 */
	private String dictCode;
	/**
	 * 字典名称
	 */
	private String dictName;
	/**
	 *状态值
	 */
	private String displayValue;
	/**
	 *状态名称
	 */
	private String displayName;

	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 不作废 or 作废
	 */
	private String delFlag;
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
}
