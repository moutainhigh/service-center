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
import com.shengsu.app.constant.ResultCode;
import com.shengsu.app.util.ResultUtil;
import com.shengsu.helper.constant.SmsSignEnum;
import com.shengsu.helper.constant.SmsTemplateEnum;
import com.shengsu.helper.service.SmsService;
import com.shengsu.result.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-10 17:21
 **/
@Slf4j
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

    @Override
    public ResultBean sendSms(String mobile, SmsTemplateEnum template, String param, SmsSignEnum smsSignEnum) {
        if (mobile == null || mobile == "") {
            log.error("手机号为空");
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_MOBILE_EMPTY);
        }
        // 初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,
                    domain);
        } catch (ClientException e1) {
            log.error("初始化ascClient失败",e1);
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        // post方式提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);
        // 必填:短信签名
        request.setSignName(smsSignEnum.getSignValue());
        // 必填:短信模板
        request.setTemplateCode(template.getTemplateCode());
        // 可选:模板中的变量替换JSON串
        request.setTemplateParam(param);
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                log.info("短信发送成功！！！");
                return ResultUtil.formResult(true, ResultCode.SUCCESS);
            }else{
                log.info(sendSmsResponse.getMessage());
                return ResultUtil.formResult(false, ResultCode.EXCEPTION_MSG_SEND,sendSmsResponse.getMessage());
            }
        } catch (ServerException e) {
            log.error("由于系统维护，暂时无法注册！！！:",e);
        } catch (ClientException e) {
            log.error("由于系统维护，暂时无法注册！！！:",e);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

}
