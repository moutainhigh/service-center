/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.shengsu.trade.pay.nuomi.request;

public class NuomiOpenApiBaseRequest extends NuomiBaseRequest {

	
	/**
	 * @description 设置需要请求的HOST
	 */
	protected String host = "http://u.nuomi.com";
	
	/**
	 * @description 默认给予一个URL,在子类中会被重载
	 */
	protected String uri  = "platform/userinfo/tokenhandler/getuserinfo";

	public NuomiOpenApiBaseRequest(){
		this.interfaceBelongToWhichPlatform = "openapi";
	}
	

	/**
	 * @description OPENAPI的拼装方式不一样
	 * @param $host
	 * @param $uri
	 * @return string
	 */
	public String getRequestUrl(){
		return this.host + "/" + this.uri;
	}
	
	
}
