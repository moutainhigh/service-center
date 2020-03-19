package com.shengsu.helper.service;

import com.shengsu.result.ResultBean;
import com.shengsu.helper.constant.MQProducerEnum;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public interface MQProducerService {
    ResultBean send(MQProducerEnum producer, String body);
}
