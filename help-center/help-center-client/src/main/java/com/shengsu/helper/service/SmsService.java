package com.shengsu.helper.service;

import com.shengsu.result.ResultBean;

/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-10 17:16
 **/
public interface SmsService {
     ResultBean sendSmsCode(String mobile, String code);
}
