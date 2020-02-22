package com.shengsu.helper.service;

import com.shengsu.helper.constant.SmsTemplateEnum;
import com.shengsu.result.ResultBean;

/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-10 17:16
 **/
public interface SmsService {
     ResultBean sendSms(String mobile, SmsTemplateEnum TemplateCode, String param);
}
