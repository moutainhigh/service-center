/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */

package com.shengsu.trade.pay.nuomi.util;


import com.shengsu.trade.pay.nuomi.util.json.JSONReader;
import com.shengsu.trade.pay.nuomi.util.json.JSONValidatingReader;

import java.util.Map;


public abstract class NuomiUtils {
    
	/**
     * 把JSON字符串转化为Map结构。
     * 
     * @param body JSON字符串
     * @return Map结构
     */
    public static Map<?, ?> parseJson(String body) {
        JSONReader jr = new JSONValidatingReader();
        Object obj = jr.read(body);
        if (obj instanceof Map<?, ?>) {
            return (Map<?, ?>) obj;
        } else {
            return null;
        }
    }

   

}
