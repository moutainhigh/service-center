package com.shengsu.helper.entity;

import com.baidubce.model.AbstractBceResponse;
import lombok.Data;

/**
 * Created on 2019/12/3
 */
@Data
public class BindResponse extends AbstractBceResponse {
    private Integer code;
    private String msg;
    private BindResult data;

    @Override
    public String toString() {
        return "code: " + this.code + " msg: " + this.msg;
    }
}
