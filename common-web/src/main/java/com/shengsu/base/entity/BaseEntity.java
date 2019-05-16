package com.shengsu.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * created:2019/4/22
 */
public class BaseEntity implements Serializable {
	/**
	 * @Fields serialVersionUID : 序列号
	 */
	private static final long serialVersionUID = 8734980094290448804L;

	/**
	 * 行数
	 */
	private Integer rows;

	/**
	 * 开始
	 */
	private Integer start;

	/**
	 * 结束
	 */
	private Integer end;

	/**
	 * 总数量
	 */
	private Integer total;
	
	/**
	 * 模糊搜索条件
	 */
	private String search;

	/**
	 * 其他参数查询条件map
	 */
	private Map<Object, Object> paramMap;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date modifyTime;
	private Integer pageSize;
	private Integer page;
	private String startTime;//创建开始时间
	private String endTime;//创建结束时间
	@JsonIgnore
	public Integer getRows() {
		return rows;
	}
	@JsonProperty
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	@JsonIgnore
	public Integer getStart() {
		return start;
	}
	@JsonProperty
	public void setStart(Integer start) {
		this.start = start;
	}
	@JsonIgnore
	public Integer getEnd() {
		return end;
	}
	@JsonProperty
	public void setEnd(Integer end) {
		this.end = end;
	}
	@JsonIgnore
	public Integer getTotal() {
		return total;
	}
	@JsonProperty
	public void setTotal(Integer total) {
		this.total = total;
	}
	@JsonIgnore
	public String getSearch() {
		return search;
	}
	@JsonProperty
	public void setSearch(String search) {
		this.search = search;
	}
	@JsonIgnore
	public Map<Object, Object> getParamMap() {
		return paramMap;
	}
	@JsonProperty
	public void setParamMap(Map<Object, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@JsonIgnore
	public Integer getPageSize() {
		return pageSize;
	}
	@JsonProperty
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@JsonIgnore
	public Integer getPage() {
		return page;
	}
	@JsonProperty
	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
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
}
