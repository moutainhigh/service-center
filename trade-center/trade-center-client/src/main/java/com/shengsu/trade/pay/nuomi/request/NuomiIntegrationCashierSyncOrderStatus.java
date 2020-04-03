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
public class NuomiIntegrationCashierSyncOrderStatus extends NuomiNopBaseRequest implements NuomiRequest<NuomiResponse>{

	protected String type;
	
	protected String bizStatus;
	
	
	/**
	 * 构造函数中进行赋值
	 */
	public NuomiIntegrationCashierSyncOrderStatus(){
		this.apiParams.put("method","nuomi.cashier.syncorderstatus");
	}
	
	
	public void setMoney(String money) {
		this.apiParams.put("money", money);
	}

	
	public void setDealTitle(String dealTitle) {
		this.apiParams.put("dealTitle", dealTitle);
	}

	
	public void setDeviceType(String deviceType) {
		this.apiParams.put("deviceType", deviceType);
	}

	public void setBizInfo(String bizInfo) {
		this.apiParams.put("bizInfo", bizInfo);
	}
	
	
}
