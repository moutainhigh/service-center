/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.common;

import com.shengsu.trade.pay.nuomi.util.NuomiHashMap;
import com.shengsu.trade.pay.nuomi.util.NuomiUtils;
import com.shengsu.trade.pay.nuomi.util.WebUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class NuomiClient{
    
    private int  connectTimeout = 2400;
    
    private int  readTimeout    = 12000;

   
    public <T extends NuomiResponse> NuomiResponse execute(NuomiRequest<T> request) throws NuomiApiException {
        
    	/**
    	 * 第一部分：执行请求
    	 */
    	Map<String, Object> rt = doPost(request);
    	
    	/**
    	 * 第二部分：如果请求为空，就返回空
    	 */
        if ( rt == null) {
            return null;
        }

        /**
         * 第三部分：开始解析返回的数据，使用responseClass
         */
        NuomiResponse rep = null;
        
        try {

        	//1.获取返回的内容
            String responseBody = (String) rt.get("response");
                        
            //2.获取解析的结果
            Map<?,?> tRsp = NuomiUtils.parseJson(responseBody);
            
            rep = new NuomiResponse();
            rep.setErrno(  tRsp.get("errno").toString() );
            rep.setMsg( tRsp.get("msg").toString() );
            rep.setData( (Object)tRsp.get("data") );
            
        } catch (RuntimeException e) {
        	//这里添加打印日志的行为操作
            throw e;
        } 
        
        return rep;
    }

    /**
     * 
     * 
     * @param request
     * @param accessToken
     * @param signType
     * @return
     * @throws NuomiApiException
     */
    private <T extends NuomiResponse> Map<String, Object> doPost(NuomiRequest<T> request ) throws NuomiApiException {
                                                                  
        
    	/**
    	 * 第一部分：初始化一些变量
    	 */
    	//1.声明并赋值请求的参数
    	NuomiHashMap reqParams = request.getApiParams();
    		
    	//2.声明请求的URL
    	String requestUrl =  request.getRequestUrl();
    	
    	//3.声明并赋值请求的平台参数
    	String requestPlatform = request.getRequestPlatform();
    	
    	/**
    	 * 第二部分：根据不同的接口选择不同的参数
    	 */
    	if( requestPlatform.equals( NuomiConstants.NOP_PLATFORM )){
    		//TODO 可能将来有一些逻辑,这次暂时没需要
    	}
    	else if ( requestPlatform.equals( NuomiConstants.OPENAPI_PLATFORM )){
    		/**
             * 6.OPENAPI的参数签名的键名是sign,不是rsaSign，这个是不一样的地方
             */
    		reqParams.put("sign",reqParams.get("rsaSign"));
    		reqParams.remove("rsaSign");
    		
    	}
        
    	
        /**
         * 第三部分:发出网络请求
         */
    	String response = null;
        
    	try {
        	response = WebUtils.doPost(requestUrl, reqParams ,connectTimeout, readTimeout); 
        } catch (IOException e) {
            throw new NuomiApiException(e);
        }
        
        Map<String, Object> result = new HashMap<String, Object>();
       
        result.put("response", response);
       
        return result;
    }

    

    
    

}
