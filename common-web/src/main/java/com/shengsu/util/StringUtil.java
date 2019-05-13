package com.shengsu.util;

import java.math.BigDecimal;

public class StringUtil {
	/**
	 * 空对象转空字符串,非空对象转字符串
	 * @param object
	 * @return
	 */
	public static String converNulltoBlank(Object object){
		return (object == null || "null".equalsIgnoreCase(object.toString())) ? "" : object.toString();
	}
	
	/**
	 * 空对象转0字符串,非空对象转字符串
	 * @param object
	 * @return
	 */
	public static String converNulltoZero(Object object){
		return object == null ? "0" : object.toString();
	}
	
	//去掉多余的0
	public static String getPrettyNumber(String number) {  
	    return BigDecimal.valueOf(Double.parseDouble(number))  
	            .stripTrailingZeros().toPlainString();  
	}
	//mysql 特殊字符进行转义
	public static String ToLikeStr(String str){
		if(str != null && str.length()>0){
			str =str.trim()
					.replace("\\", "/\\")
					.replace("%", "/%")
					.replace("_", "/_")
					.replace("'", "/'");
		}
		return str ;
	}
}

