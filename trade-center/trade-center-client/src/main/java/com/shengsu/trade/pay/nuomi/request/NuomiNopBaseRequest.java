/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */


package com.shengsu.trade.pay.nuomi.request;

public class NuomiNopBaseRequest extends NuomiBaseRequest {
	
	protected String orderId;
	
	protected String dealId;
	
	protected String userId;
	
	protected String tpOrderId;
	
	protected String method;
	
	protected String bizInfo;
	
	protected String bizStatus;
	
	/**
	 * @description 设置需要请求的HOST
	 */
	protected String host = "http://nop.nuomi.com/nop/server/rest";

	
	/**
	 * 构造函数，初始化一些参数
	 */
	public NuomiNopBaseRequest()
	{
		/**
		 * 设置发送的平台
		 */
		this.interfaceBelongToWhichPlatform = "nop";	
	}
	
	/**
	 * @notice 设置tpOrderId
	 * @param tpOrderId
	 */
	public void setTpOrderId(String tpOrderId) {
		this.apiParams.put("tpOrderId", tpOrderId);
	}
	
	/**
	 * @notice 设置tpOrderId
	 * @param tpOrderId
	 */
	public void setDealId(String dealId) {
		this.apiParams.put("dealId", dealId);	
	}
	
	/**
	 * @notice 设置tpOrderId
	 * @param tpOrderId
	 */
	public void setUserId(String userId) {
		this.apiParams.put("userId", userId);
	}
	
	/**
	 * @notice 设置orderId
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
		this.apiParams.put("orderId", orderId);
	}
	
	/**
	 * @notice 设置bizInfo
	 */
	public void setBizInfo(String bizInfo) {
		this.apiParams.put("bizInfo", bizInfo);
	}
	
	
	/**
	 * @notice 设置bizStatus
	 */
	public void setBizStatus(String bizStatus) {
		this.apiParams.put("bizStatus", bizStatus);
	}
	
	
	
	/**
	 * @notice 获取请求的URL
	 * @return 
	 */
	public String getRequestUrl()
	{
		return this.host;
	}
	

	
	

	
}
