package com.shengsu.helper.entity;

import com.baidubce.auth.BceCredentials;
import com.baidubce.model.AbstractBceRequest;

/**
 * Created on 2019/12/3
 */
public class AxBindRequest extends AbstractBceRequest {
    private String telA;
    private String telX;
    private String areaCode;
    private Integer record;
    private Integer expiration;
    private String customer;

    public Integer getExpiration() {
        return expiration;
    }

    public Integer getRecord() {
        return record;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getCustomer() {
        return customer;
    }

    public String getTelA() {
        return telA;
    }

    public String getTelX() {
        return telX;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public void setTelA(String telA) {
        this.telA = telA;
    }

    public void setTelX(String telX) {
        this.telX = telX;
    }

    @Override
    public AxBindRequest withRequestCredentials(BceCredentials bceCredentials) {
        this.setRequestCredentials(bceCredentials);
        return this;
    }
}
