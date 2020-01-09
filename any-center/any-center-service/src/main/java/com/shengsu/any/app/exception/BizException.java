package com.shengsu.any.app.exception;


import com.shengsu.any.app.constant.ResultCode;

/**
 * 业务异常类(异常code和消息与ResultCode相对应)
 * @author zxh
 *
 */
public class BizException extends Exception {
	private static final long serialVersionUID = 1L;
	private Integer code;
	public BizException(String message) {
		super(message);
	}
	// 根据结果返回消息构造异常
	public BizException(ResultCode resultCode){
		super(resultCode.getResultMessage());
		this.code = resultCode.getCode();
	}
	public Integer getCode() {
		return code;
	}
}
