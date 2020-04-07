/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */


package com.shengsu.trade.pay.nuomi.request;

import com.shengsu.trade.pay.nuomi.common.NuomiRequest;
import com.shengsu.trade.pay.nuomi.common.NuomiResponse;


/**
 * NUOMI API: NuomiIntegrationCashierGetUserInfoRequest
 */
public class NuomiIntegrationCashierGetUserInfoRequest extends NuomiOpenApiBaseRequest implements NuomiRequest<NuomiResponse>{

	protected String uri = "platform/userinfo/tokenhandler/getuserinfo";
	
	/**
	 * @notice 获取用户信息接口独有的参数
	 * @param string $token 用户校验的令牌环值
	 */
	public void setToken(String  token ){
		this.apiParams.put("token", token);
	}

	
}
