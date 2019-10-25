package com.shengsu.helper.service;

import com.shengsu.app.constant.ResultBean;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public interface ProducerService {

    ResultBean rocketMqSend(String topic, String tags, String body) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException;
}
