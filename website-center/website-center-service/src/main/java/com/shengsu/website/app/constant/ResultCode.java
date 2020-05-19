package com.shengsu.website.app.constant;

import com.shengsu.result.IResultCode;

/**
 * 防重类型
 * @author zxh
 *
 */
public enum ResultCode implements IResultCode {
    SUCCESS(1000, "成功"),
    FAIL(1001, "失败"),
    EXCEPTION(1002, "异常"),
    EXCEPTION_PARAM_NULL(1002, "参数为空"),

    //咨询
    EXCEPTION_CONSULT_APPENDIX_REFID_IS_NULL(1002, "关联id错误"),
    EXCEPTION_PROBLEM_ALREADY_EXISTS(1002,"问题已存在"),
    //登录
    EXCEPTION_WECHAT_LOGIN_CODE_IS_NULL(1002,"code为空"),
    EXCEPTION_LOGIN_TOKEN_EXPIRED(1003,"授权信息已过期"),
    EXCEPTION_LOGIN_TOKEN_INVALID(1004,"无效的认证"),
    EXCEPTION_WECHAT_RESPONSE_ERROR(1002,"微信响应错误"),

    //新闻中心
    NEWS_DATA_REPEAT(1002,"新闻中心:数据重复"),
    NEWS_ID_ERROR(1002,"新闻中心:id错误"),

    //成功案例
    CASE_DATA_REPEAT(1002,"成功案例:数据重复"),
    CASE_ID_ERROR(1002,"成功案例:id错误"),

//    轮番播放
    WHEEL_ID_ERROR(1002,"轮番播放:无此用户"),

    //法律知识文库
    LAW_KNOWLEDGE_ID_ERROR(1002,"法律知识文库:id错误"),
    LAW_KNOWLEDGE_DATA_REPEAT(1002,"法律知识文库:数据重复");

    private Integer code;
    private String  resultMessage;

    ResultCode(Integer code, String resultMessage) {
        this.code = code;
        this.resultMessage = resultMessage;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

    public static ResultCode getByCode(Integer code){
        for(ResultCode resultCode:ResultCode.values()){
            if(resultCode.getCode()==code){
                return resultCode;
            }
        }
        return null;
    }
}
