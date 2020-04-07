/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.request;

import com.shengsu.trade.pay.nuomi.common.NuomiRequest;
import com.shengsu.trade.pay.nuomi.common.NuomiResponse;

public class NuomiIntegrationCashierBizCreateOrderRequest extends NuomiNopBaseRequest implements NuomiRequest<NuomiResponse>{

	protected String money;
	
	protected String dealTitle;
	
	protected String deviceType;
	
	
	/**
	 * 构造函数中进行赋值
	 */
	public NuomiIntegrationCashierBizCreateOrderRequest(){
		this.apiParams.put("method","nuomi.cashier.bizcreateorder");
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


	
	
}
