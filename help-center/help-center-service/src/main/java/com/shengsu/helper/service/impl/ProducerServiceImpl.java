package com.shengsu.helper.service.impl;


import com.shengsu.app.constant.ResultBean;
import com.shengsu.app.constant.ResultCode;
import com.shengsu.app.util.ResultUtil;
import com.shengsu.helper.constant.ProducerEnum;
import com.shengsu.helper.service.ProducerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * Created by zyc on 2019/10/12.
 */
@Service(value = "producerService")
public class ProducerServiceImpl implements ProducerService {

    public static final Logger log = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.producer.logGroup}")
    private String logGroup;
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;

    private DefaultMQProducer rocketMqProducer;

    @PostConstruct
    public void init() {

        rocketMqProducer = new DefaultMQProducer(logGroup);
        rocketMqProducer.setNamesrvAddr(namesrvAddr);
        rocketMqProducer.setMaxMessageSize(maxMessageSize);
        rocketMqProducer.setSendMsgTimeout(sendMsgTimeout);
        rocketMqProducer.setVipChannelEnabled(false);

        try {
            rocketMqProducer.start();
            log.info("rocketMQ is start !!groupName : {},nameserAddr:{}", logGroup, namesrvAddr);
        } catch (MQClientException e) {
            log.error(String.format("rocketMQ start error,{}", e.getMessage()));
            e.printStackTrace();
        }
    }

    public ResultBean rocketMqSend(ProducerEnum producer, String body) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        if (producer == null || StringUtils.isBlank(body)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_LOGIN_PARAM_ERROR);
        }

        Message message = new Message(producer.getTopic(), producer.getTag(), body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        StopWatch stop = new StopWatch();
        stop.start();
        SendResult result = rocketMqProducer.send(message);
        log.info("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
        stop.stop();

        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

}
