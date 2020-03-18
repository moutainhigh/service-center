package com.shengsu.helper.constant;

/**
 * 短信签名
 */
public enum SmsSignEnum {
    SMS_SIGN_CODE_YS("YS","浙江援手律师事务所"),
    SMS_SIGN_CODE_SSKJ("SSKJ","胜诉科技");

    private String signCode;
    private String signValue;

    SmsSignEnum(String signCode,String signValue){
        this.signCode = signCode;
        this.signValue = signValue;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getSignValue() {
        return signValue;
    }

    public void setSignValue(String signValue) {
        this.signValue = signValue;
    }
}
