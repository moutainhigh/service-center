package com.shengsu.helper.entity;

import com.baidubce.auth.BceCredentials;
import com.baidubce.model.AbstractBceRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2019/12/3
 */
@Data
public class AxbBindRequest extends AbstractBceRequest implements Serializable {
    private String telA;
    private String telB;
    private String telX;
    private String areaCode;
    private Integer record;
    private Integer expiration;
    private String customer;

    @Override
    public AxbBindRequest withRequestCredentials(BceCredentials bceCredentials) {
        this.setRequestCredentials(bceCredentials);
        return this;
    }
}
