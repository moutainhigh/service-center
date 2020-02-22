package com.shengsu.helper.entity;

import com.baidubce.auth.BceCredentials;
import com.baidubce.model.AbstractBceRequest;
import lombok.Data;

/**
 * Created on 2019/12/3
 */
@Data
public class AxBindRequest extends AbstractBceRequest {
    private String telA;
    private String telX;
    private String areaCode;
    private Integer record;
    private Integer expiration;
    private String customer;

    @Override
    public AxBindRequest withRequestCredentials(BceCredentials bceCredentials) {
        this.setRequestCredentials(bceCredentials);
        return this;
    }
}
