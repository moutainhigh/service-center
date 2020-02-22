package com.shengsu.helper.entity;

import com.baidubce.model.AbstractBceResponse;

/**
 * Created on 2019/12/3
 */
public class BindResponse extends AbstractBceResponse {
    private Integer code;
    private String msg;
    private BindResult data;

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(BindResult data) {
        this.data = data;
    }

    public BindResult getData() {
        return data;
    }

    @Override
    public String toString() {
        return "code: " + this.code + " msg: " + this.msg;
    }
}
