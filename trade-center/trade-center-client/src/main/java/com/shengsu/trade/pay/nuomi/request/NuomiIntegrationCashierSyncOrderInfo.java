/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.request;

import com.shengsu.trade.pay.nuomi.common.NuomiRequest;
import com.shengsu.trade.pay.nuomi.common.NuomiResponse;


/**
 * NUOMI API: nuomi.pass.code.verify request
 * 
 * @author auto create
 * @since 1.0, 2014-06-12 17:16:11
 */
public class NuomiIntegrationCashierSyncOrderInfo extends NuomiNopBaseRequest implements NuomiRequest<NuomiResponse>{

	protected String payMoney;
	
	protected String mobile;
	
	protected String cuid;
	
	protected String deviceType;
	
	protected String itemDetail;
	
	protected String status;
	
	protected String updateTime;
	
	protected String payTime;
	
	protected String refundInfo;
	
	protected String payInfo;
	
	protected String tailPayInfo;

	
	/**
	 * 构造函数中进行赋值
	 */
	public NuomiIntegrationCashierSyncOrderInfo(){
		this.apiParams.put("method","nuomi.cashier.syncorderinfo");
	}
	
	
	public void setPayMoney(String payMoney) {
		this.apiParams.put("payMoney", payMoney);
	}

	
	public void setMobile(String mobile) {
		this.apiParams.put("mobile", mobile);
	}

	
	public void setCuid(String cuid) {
		this.apiParams.put("cuid", cuid);
	}
	
	public void setDeviceType(String deviceType) {
		this.apiParams.put("deviceType", deviceType);
	}

	public void setItemDetail(String deviceType) {
		this.apiParams.put("deviceType", deviceType);
	}
	
	public void setStatus(String status) {
		this.apiParams.put("status", status);
	}
	
	public void setUpdateTime(String updateTime) {
		this.apiParams.put("updateTime", updateTime);
	}
	
	public void setPayTime(String payTime) {
		this.apiParams.put("payTime", payTime);
	}
	
	public void setRefundInfo(String refundInfo) {
		this.apiParams.put("refundInfo", refundInfo);
	}
	
	public void setPayInfo(String payInfo) {
		this.apiParams.put("payInfo", payInfo);
	}
	
	public void setTailPayInfo(String tailPayInfo) {
		this.apiParams.put("tailPayInfo", tailPayInfo);
	}
	
	
}
