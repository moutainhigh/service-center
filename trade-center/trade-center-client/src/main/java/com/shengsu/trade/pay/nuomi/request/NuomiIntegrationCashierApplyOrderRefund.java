/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.request;

import com.shengsu.trade.pay.nuomi.common.NuomiRequest;
import com.shengsu.trade.pay.nuomi.common.NuomiResponse;

public class NuomiIntegrationCashierApplyOrderRefund extends NuomiNopBaseRequest implements NuomiRequest<NuomiResponse>{

	protected String refundType;
	
	protected String refundReason;
	
	
	/**
	 * 构造函数中进行赋值
	 */
	public NuomiIntegrationCashierApplyOrderRefund(){
		this.apiParams.put("method","nuomi.cashier.applyorderrefund");
	}
	
	
	public void setRefundType(String refundType) {
		this.apiParams.put("refundType", refundType);
	}

	
	public void setRefundReason(String refundReason) {
		this.apiParams.put("refundReason", refundReason);
	}
	
	
}
