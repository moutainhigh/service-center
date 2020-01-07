package com.shengsu.any.app.constant;

/**
 * 防重类型
 * @author zxh
 *
 */
public enum ResultCode {
    SUCCESS(1000, "成功"),
    FAIL(1001, "失败"),
    EXCEPTION(1002, "异常"),
    EXCEPTION_RPC(1002, "rpc异常"),
    EXCEPTION_PARAM_PARSE_FAIL(1002, "参数解析失败"),
    EXCEPTION_PARAM_VALID_FAIL(1002, "参数校验失败"),
    //登录
    EXCEPTION_LOGIN_PARAM_ERROR(1002,"参数错误"),
    EXCEPTION_LOGIN_TOKEN_EXPIRED(1003,"授权信息已过期"),
    EXCEPTION_LOGIN_TOKEN_INVALID(1004,"无效的认证"),
    //获取token
    EXCEPTION_PARAM_ERROR(1002,"参数有误"),
    //数据校验
    EXCEPTION_DATA_EXISTENCE(1002,"数据已存在"),
    EXCEPTION_NOT_NATA_EXISTENCE(1002,"数据不存在"),
    EXCEPTION_DUPLICATE_ACTION(1002,"重复操作");

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
