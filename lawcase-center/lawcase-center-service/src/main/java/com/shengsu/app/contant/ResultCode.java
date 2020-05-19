package com.shengsu.app.contant;

import com.shengsu.result.IResultCode;

/**
 * 防重类型
 * @author zxh
 *
 */
public enum ResultCode implements IResultCode {
    SUCCESS(1000, "成功"),
    FAIL(1001, "失败"),
    EXCEPTION(1002, "异常");


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
