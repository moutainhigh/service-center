package com.shengsu.helper.entity;

import com.baidubce.auth.BceCredentials;
import com.baidubce.model.AbstractBceRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-26 15:41
 **/
@Data
public class AxbUnBindRequest extends AbstractBceRequest implements Serializable {
    private String bindId;

    @Override
    public AxbUnBindRequest withRequestCredentials(BceCredentials bceCredentials) {
        this.setRequestCredentials(bceCredentials);
        return this;
    }
}
