package com.shengsu.helper.constant;

/**
 * Created by Bell on 2019/10/30.
 */
public enum SmsTemplateEnum {

    SMS_180053728("SMS_180053728"),//注册验证码
    SMS_184115275("SMS_184115275"), //发送客户
    SMS_184105294("SMS_184105294"); //发送律师

    private String templateCode;

    SmsTemplateEnum(String templateCode){
        this.templateCode = templateCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}