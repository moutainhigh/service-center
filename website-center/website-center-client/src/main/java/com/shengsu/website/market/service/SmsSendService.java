package com.shengsu.website.market.service;

import com.shengsu.result.ResultBean;
import com.shengsu.website.market.vo.SmsSendVo;

public interface SmsSendService {
    ResultBean sendSms(SmsSendVo smsSendVo);
    ResultBean checkPhoneValidationCode(SmsSendVo smsSendVo);
}
