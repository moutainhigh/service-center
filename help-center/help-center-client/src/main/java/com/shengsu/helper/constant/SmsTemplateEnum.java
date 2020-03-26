package com.shengsu.helper.constant;

/**
 * Created by Bell on 2019/10/30.
 */
public enum SmsTemplateEnum {

    SMS_180053728("SMS_180053728"),//注册验证码
    SMS_184115275("SMS_184115275"), //发送客户
    SMS_186615861("SMS_186615861"),//律师号码发送客户
    SMS_186613636("SMS_186613636"),//修改发送律师短信模板
    SMS_186381003("SMS_186381003");//发送律师


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
