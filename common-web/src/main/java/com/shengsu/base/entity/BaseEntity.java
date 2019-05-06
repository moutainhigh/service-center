package com.shengsu.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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
	
    private Date createTime;
    private Date modifyTime;
	
}
