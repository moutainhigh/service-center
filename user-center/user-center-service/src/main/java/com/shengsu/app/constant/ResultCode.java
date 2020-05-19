package com.shengsu.app.constant;

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
    //登录
    EXCEPTION_LOGIN_PARAM_ERROR(1002,"参数错误"),
    EXCEPTION_LOGIN_USERNAME_PASSWROD_ERROR(1002,"用户名或密码错误"),
    EXCEPTION_LOGIN_TOKEN_EXPIRED(1003,"授权信息已过期"),
    EXCEPTION_LOGIN_TOKEN_INVALID(1004,"无效的认证"),
    //注册
    EXCEPTION_REGISTER_USER_EXISTED(1002,"用户已存在"),
    EXCEPTION_REGISTER_USER_NOT_EXISTED(1002,"用户不存在"),

    //用户
    EXCEPTION_USER_PASSWROD_DISAFFINITY(1002,"旧密码不正确");

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
        for(ResultCode resultCode: ResultCode.values()){
            if(resultCode.getCode()==code){
                return resultCode;
            }
        }
        return null;
    }
}
