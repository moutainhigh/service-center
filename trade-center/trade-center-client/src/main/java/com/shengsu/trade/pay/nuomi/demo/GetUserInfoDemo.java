/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.demo;

import com.shengsu.trade.pay.nuomi.common.NuomiApiException;
import com.shengsu.trade.pay.nuomi.common.NuomiClient;
import com.shengsu.trade.pay.nuomi.common.NuomiConstants;
import com.shengsu.trade.pay.nuomi.common.NuomiResponse;
import com.shengsu.trade.pay.nuomi.request.NuomiIntegrationCashierGetUserInfoRequest;
import com.shengsu.trade.pay.nuomi.util.NuomiHashMap;
import com.shengsu.trade.pay.nuomi.util.NuomiSignature;

public class GetUserInfoDemo {

	public static void main(String[] args) throws NuomiApiException {
		
		/**
		 * 第一部分：进行一些初始化操作
		 */
		NuomiClient client = new NuomiClient();
		
		NuomiIntegrationCashierGetUserInfoRequest request = new NuomiIntegrationCashierGetUserInfoRequest();
		
		/**
		 * 第二部分：设置请求的参数
		 */
		request.setAppKey("MMM4hk");
		
		request.setToken("dfgfdgdfgfdgdfgdfg");
		
	
		/**
		 * 第三部分：计算签名
		 */
		/**
		 * 1.通过方法获取到所有需要需要参与签名的参数HashMap
		 */
		NuomiHashMap apiParams = request.getApiParams();
		
		/**
		 * 2.从常量中读取privateKey,然后计算RSA签名
		 */
		String rsaSign = NuomiSignature.genSignWithRsa( apiParams , NuomiConstants.PRIVATE_KEY );
				
		/**
		 * 3.将计算完的签名反填到request类中
		 */
		request.setRsaSign(rsaSign);
		
		
		/**
		 * 第四部分：执行请求并处理结果
		 */
		//1.执行请求
		NuomiResponse response = client.execute(request);
		
		//2.处理调用结果
		System.out.println( response.getErrno() );
		System.out.println( response.getMsg() );
		System.out.println( response.getData() );
		
		
	}

}
