package com.shengsu.app.util;

import com.shengsu.app.constant.CommonConst;
import com.shengsu.app.constant.ResultBean;
import com.shengsu.app.constant.ResultCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 结果工具
 * @author zxh
 *
 */
public class ResultUtil implements Serializable {

	public static ResultBean formResult(boolean success, ResultCode resultCode) {
		return new ResultBean(success, resultCode.getCode(), resultCode.getResultMessage());
	}
	public static ResultBean formResult(boolean success, ResultCode resultCode, Object body) {
		return new ResultBean(success, resultCode.getCode(), resultCode.getResultMessage(), body);
	}
	/**
	 * 转换空值返回结构
	 * @return
	 */
	public static ResultBean formNullResult(){
		HashMap<String, Object> map = new HashMap<>();
		map.put(CommonConst.ROOT, new ArrayList<>());
		map.put(CommonConst.TOTAL_COUNT, 0);
		return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
	}
}
