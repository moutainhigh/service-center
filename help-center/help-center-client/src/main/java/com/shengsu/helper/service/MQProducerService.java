package com.shengsu.helper.service;

import com.shengsu.helper.constant.MQProducerEnum;
import com.shengsu.result.ResultBean;

public interface MQProducerService {
    ResultBean send(MQProducerEnum producer, String body);
    ResultBean sendDelay(MQProducerEnum producer, String body, int delayLevel);
}
