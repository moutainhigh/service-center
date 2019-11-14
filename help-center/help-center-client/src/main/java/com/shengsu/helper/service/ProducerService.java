package com.shengsu.helper.service;

import com.shengsu.result.ResultBean;
import com.shengsu.helper.constant.ProducerEnum;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public interface ProducerService {

    ResultBean rocketMqSend(ProducerEnum producer, String body) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException;
}
