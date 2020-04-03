/**
 * nuomi.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */


package com.shengsu.trade.pay.nuomi.util;

import java.util.HashMap;
import java.util.Map;


public class NuomiHashMap extends HashMap<String, String> {

	private static final long serialVersionUID = -1277791390393392630L;

	public NuomiHashMap() {
		super();
	}

	public NuomiHashMap(Map<? extends String, ? extends String> m) {
		super(m);
	}

	public String put(String key, Object value) {
		String strValue;

		if (value == null) {
			strValue = null;
		} 
		else if (value instanceof String) {
			strValue = (String) value;
		} 
		else if (value instanceof Integer) {
			strValue = ((Integer) value).toString();
		} 
		else if (value instanceof Long) {
			strValue = ((Long) value).toString();
		} 
		else if (value instanceof Float) {
			strValue = ((Float) value).toString();
		} 
		else if (value instanceof Double) {
			strValue = ((Double) value).toString();
		} 
		else if (value instanceof Boolean) {
			strValue = ((Boolean) value).toString();
		} 
		else {
			strValue = value.toString();
		}

		return this.put(key, strValue);
	}

	public String put(String key, String value) {
		if (StringUtils.areNotEmpty(key, value)) {
			return super.put(key, value);
		} else {
			return null;
		}
	}

}
