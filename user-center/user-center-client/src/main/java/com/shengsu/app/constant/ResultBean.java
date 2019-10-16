package com.shengsu.app.constant;

import java.io.Serializable;

/**
 * @author zxh
 * @ClassName: ResultBean
 * @Description: 返回结果类
 */
public class ResultBean<T> implements Serializable {
    /**
     * @Fields serialVersionUID : 序列号
     */
    private static final long serialVersionUID = 3752896059260841051L;

    /**
     * 调用成功标识
     */
    private boolean success;

    /**
     * 结果代码
     */
    private Integer resultCode;

    /**
     * 结果信息
     */
    private String resultMsg;

    /**
     * 内容体
     */
    private T body;

    public ResultBean() {
    }

    public ResultBean(boolean success, Integer resultCode, String resultMsg,
                      T body) {
        this.success = success;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.body = body;
    }

    public ResultBean(boolean success, Integer resultCode, String resultMsg) {
        this.success = success;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    /**
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success 要设置的 success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * @return resultMsg
     */
    public String getResultMsg() {
        return resultMsg;
    }

    /**
     * @param resultMsg 要设置的 resultMsg
     */
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    /**
     * @return body
     */
    public T getBody() {
        return body;
    }

    /**
     * @param body 要设置的 body
     */
    public void setBody(T body) {
        this.body = body;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }
}
