package com.shengsu.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Map<Object, Object> getParamMap() {
		return paramMap;
	}

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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
