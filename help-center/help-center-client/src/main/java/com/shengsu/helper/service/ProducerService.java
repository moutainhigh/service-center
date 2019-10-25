package com.shengsu.helper.service;

import com.shengsu.app.constant.ResultBean;
import com.shengsu.helper.entity.Producer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public interface ProducerService {

    ResultBean rocketMqSend(Producer producer, String body) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException;
}
