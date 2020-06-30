package com.shengsu.helper.service;

import com.shengsu.helper.constant.MQEnum;
import com.shengsu.result.ResultBean;

public interface MQProducerService {
    ResultBean send(MQEnum producer, String body);
    ResultBean sendDelay(MQEnum producer, String body, int delayLevel);
}
