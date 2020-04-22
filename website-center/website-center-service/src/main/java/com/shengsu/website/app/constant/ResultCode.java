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
    EXCEPTION_RPC(1002, "rpc异常"),
    EXCEPTION_PARAM_PARSE_FAIL(1002, "参数解析失败"),
    EXCEPTION_PARAM_VALID_FAIL(1002, "参数校验失败"),

    //咨询
    EXCEPTION_CONSULT_APPENDIX_REFID_IS_NULL(1002, "关联id错误"),
    //登录
    FAIL_LOGIN_PARAM_ERROR(1001,"参数错误"),
    FAIL_LOGIN_USERNAME_EMPTY(1001,"用户名为空"),
    FAIL_LOGIN_PASSWORD_EMPTY(1001,"密码为空"),
    FAIL_LOGIN_USERNAME_PASSWROD_ERROR(1001,"用户名或密码错误"),
    EXCEPTION_LOGIN_TOKEN_EXPIRED(1003,"授权信息已过期"),
    EXCEPTION_LOGIN_TOKEN_INVALID(1004,"无效的认证"),
    
    //注册
    FAIL_REGISTER_USER_EXISTED(1001,"用户已存在"),
    
    //用户
    FAIL_USER_PASSWROD_DISAFFINITY(1001,"旧密码不正确"),
    
    //案件

    EXCEPTION_DUPLICATE_ACTION(1002,"重复操作"),

    //新闻中心
    NEWS_PARAM_ERROR(1002,"新闻中心:参数错误"),
    NEWS_DATA_REPEAT(1002,"新闻中心:数据重复"),
    NEWS_ID_ERROR(1002,"新闻中心:id错误"),

    //成功案例
    CASE_PARAM_ERROR(1002,"成功案例:参数错误"),
    CASE_DATA_REPEAT(1002,"成功案例:数据重复"),
    CASE_ID_ERROR(1002,"成功案例:id错误"),

//    轮番播放
    WHEEL_DATA_REPEAT(1002,"轮番播放：数据重复"),
    WHEEL_ID_ERROR(1002,"轮番播放:无此用户"),

    //法律知识文库
    LAW_KNOWLEDGE_ID_ERROR(1002,"法律知识文库:id错误");


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
