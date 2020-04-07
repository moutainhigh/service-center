/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */


package com.shengsu.trade.pay.nuomi.demo;

import com.shengsu.trade.pay.nuomi.common.NuomiApiException;
import com.shengsu.trade.pay.nuomi.common.NuomiClient;
import com.shengsu.trade.pay.nuomi.common.NuomiConstants;
import com.shengsu.trade.pay.nuomi.common.NuomiResponse;
import com.shengsu.trade.pay.nuomi.request.NuomiIntegrationCashierBizCreateOrderRequest;
import com.shengsu.trade.pay.nuomi.util.NuomiHashMap;
import com.shengsu.trade.pay.nuomi.util.NuomiSignature;

public class BizCreateOrderDemo {

	public static void main(String[] args) throws NuomiApiException {
		
		
		/**
		 * 第一部分：进行一些初始化操作
		 */
		NuomiClient client = new NuomiClient();
		
		NuomiIntegrationCashierBizCreateOrderRequest request = new NuomiIntegrationCashierBizCreateOrderRequest();
		
		String bizInfo = "{\"tpData\":{\"appKey\":\"MMUv5L\",\"dealId\":\"671485529\",\"tpOrderId\":\"11119800\",\"rsaSign\":\"FQNh12D/qwGqINHFmA79HZx2sLQBdLiA+NP3EU+x0CL6AFqFJKF9XcP890qlOzvhxOqYa2M3TPurKwOAWzqQdTsNdA1QzTXWROZjdJSG5zYsFF9JcWYiKsSJ1CcERI1NI2bGos4IHiVf35y1vieHVxmCpJmXiGsEjKPM09ogcTg=\",\"totalAmount\":\"0.01\",\"payResultUrl\":\"\",\"returnData\":{\"bizKey1\":\"第三方的字段1取值\",\"bizKey2\":\"第三方的字段2取值\"},\"displayData\":{\"cashierTopBlock\":[[{\"leftCol\":\"订单名称\",\"rightCol\":\"爱鲜蜂\"},{\"leftCol\":\"数量\",\"rightCol\":\"1\"},{\"leftCol\":\"小计\",\"rightCol\":\"113\"}]]},\"dealTitle\":\"爱鲜蜂\",\"dealSubTitle\":\"爱鲜蜂副标题\",\"dealThumbView\":\"http://i1.sinaimg.cn/lx/news/2008-12-30/U1043P622T1D29849F15DT20081230120718.jpg\"},\"orderDetailData\":{\"displayData\":{\"tpOrderInfoBlock\":{\"title\":\"服务明细\",\"content\":[{\"leftCol\":\"服务地址\",\"rightCol\":\"北京市海淀区上地东北旺西路10号百度科技园\"},{\"leftCol\":\"服务时间\",\"rightCol\":\"2015/06/06 13:00-15:00\"}]}}}}";
		
		/**
		 * 第二部分：设置相关系统参数
		 */
		request.setAppKey("MMUv5L");
		
		request.setDealId("671485529");
		
		request.setUserId("2385619614");
		
		request.setMoney("0.01");
		
		request.setTpOrderId("11119800");
		
		request.setDealTitle("SDK测试单子");
		
		request.setDeviceType("ANDROID");
		
		request.setBizInfo(bizInfo);
		
		
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
		String rsaSign = NuomiSignature.genSignWithRsa(apiParams,NuomiConstants.PRIVATE_KEY);
        System.out.println(rsaSign);
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
		System.out.println( response.getMsg()   );
		System.out.println( response.getData()  );
	
	}

}
