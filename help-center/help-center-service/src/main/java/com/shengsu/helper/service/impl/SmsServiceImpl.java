package com.shengsu.helper.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.shengsu.helper.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-10 17:21
 **/
@Service(value = "smsService")
public class SmsServiceImpl implements SmsService {
    @Value("${sms.product}")
    private String product;
    @Value("${sms.domain}")
    private String domain;
    @Value("${sms.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${sms.signName}")
    private String signName;
    @Value("${sms.templateCode}")
    private String templateCode;
    @Override
    public String sendSms(String mobile) {
        if (mobile == null || mobile == "") {
            System.out.println("手机号为空");
            return "";
        }
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,
                    domain);
        } catch (ClientException e1) {
            e1.printStackTrace();
        }

        //获取验证码
        String code = vcode();

        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(mobile);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{ \"code\":\""+code+"\"}");
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null
                    && sendSmsResponse.getCode().equals("OK")) {
                System.out.println("获取验证码成功！！！");
            } else {
                System.out.println(sendSmsResponse.getCode());
                System.out.println("获取验证码失败...");
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return "由于系统维护，暂时无法注册！！！";
        } catch (ClientException e) {
            e.printStackTrace();
            return "由于系统维护，暂时无法注册！！！";
        }
        return code;
    }

    private String vcode() {
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
}
