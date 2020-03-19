package com.shengsu.website.app.util;

import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;

/**
 * 结果工具
 * @author zxh
 *
 */
public class ResultUtil {

	public static <T> ResultBean<T> formResult(boolean success, ResultCode resultCode, T body){
		return new ResultBean(success, resultCode.getCode(), resultCode.getResultMessage(), body);
	}

}
