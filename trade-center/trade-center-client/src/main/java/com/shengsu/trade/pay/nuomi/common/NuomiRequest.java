/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.common;


import com.shengsu.trade.pay.nuomi.util.NuomiHashMap;

/**
 * 请求接口。
 */
public interface NuomiRequest<T extends NuomiResponse>{

   
    public NuomiHashMap getApiParams();

    
    /**
     * @notice 无论是OPENAPI还是NOP都需要设置appKey参数，所以放到基类中来
     * @param int $appKey
     */
    public void setAppKey(String apiVersion);
    
    
    /**
     * @notice 无论是OPENAPI还是NOP都需要设置rsaSign参数，所以放到基类中来
     */
    public void setRsaSign(String rsaSign);
    
    /**
     * @notice 获取平台的类型 getRequestPlatform
     */
    public String getRequestPlatform();
    
    
    /**
     * @notice 获取HTTP的请求地址
     */
    public String getRequestUrl();
  
   
    
}
