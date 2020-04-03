/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.request;

import com.shengsu.trade.pay.nuomi.util.NuomiHashMap;

public class NuomiBaseRequest{

	protected String interfaceBelongToWhichPlatform = null;

    /**
     * 接口最后的使用的API参数
     */
    protected NuomiHashMap apiParams;
    
    /**
     * 构造函数的时候要初始化，否则下面setAppKey,setRsaSign,setToken都不对
     */
    public NuomiBaseRequest(){
    	this.apiParams = new NuomiHashMap();
    }
    
    public NuomiHashMap getApiParams()
    {
        return this.apiParams;
    }

    public String getRequestPlatform()
    {
    	return this.interfaceBelongToWhichPlatform;    
    }

    /**
     * @notice 无论是OPENAPI还是NOP都需要设置appKey参数，所以放到基类中来
     * @param int $appKey
     */
    public void setAppKey(String appKey){
    	this.apiParams.put("appKey", appKey);
    }
    

    /**
     * @notice 无论是OPENAPI还是NOP都需要设置rsaSign参数，所以放到基类中来
     */
    public void setRsaSign(String rsaSign){
    	this.apiParams.put("rsaSign", rsaSign);
    }
    
	
}
