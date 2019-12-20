package com.shengsu.helper.service.impl;


import com.shengsu.app.constant.ResultCode;
import com.shengsu.app.util.ResultUtil;
import com.shengsu.helper.constant.MQProducerEnum;
import com.shengsu.helper.service.MQProducerService;
import com.shengsu.result.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * Created by zyc on 2019/10/12.
 */
@Slf4j
@Service(value = "mqProducerService")
public class MQProducerServiceImpl implements MQProducerService {

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.producer.logGroup}")
    private String logGroup;
    @Value("${rocketmq.producer.jpushGroup}")
    private String jpushGroup;
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;

    private DefaultMQProducer logRocketMqProducer;

    private DefaultMQProducer jpushRocketMqProducer;

    @PostConstruct
    public void init() {

        logRocketMqProducer = new DefaultMQProducer(logGroup);
        logRocketMqProducer.setNamesrvAddr(namesrvAddr);
        logRocketMqProducer.setMaxMessageSize(maxMessageSize);
        logRocketMqProducer.setSendMsgTimeout(sendMsgTimeout);
        logRocketMqProducer.setVipChannelEnabled(false);

        jpushRocketMqProducer = new DefaultMQProducer(jpushGroup);
        jpushRocketMqProducer.setNamesrvAddr(namesrvAddr);
        jpushRocketMqProducer.setMaxMessageSize(maxMessageSize);
        jpushRocketMqProducer.setSendMsgTimeout(sendMsgTimeout);
        jpushRocketMqProducer.setVipChannelEnabled(false);
        try {
            logRocketMqProducer.start();
            jpushRocketMqProducer.start();
            log.info("rocketMQ is start !!groupName : {},nameserAddr:{}", logGroup, namesrvAddr);
        } catch (MQClientException e) {
            log.error(String.format("rocketMQ start error,{}", e));
        }
    }

    public ResultBean send(MQProducerEnum producer, String body) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        if (producer == null || StringUtils.isBlank(body)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_PARAM_ERROR);
        }

        Message message = new Message(producer.getTopic(), producer.getTag(), body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        StopWatch stop = new StopWatch();
        stop.start();
        SendResult result = null;
        if(MQProducerEnum.JPUSHSCHEDULE.getTopic().equals(producer.getTag())){
             result = jpushRocketMqProducer.send(message);
        }else {
             result = logRocketMqProducer.send(message);
        }

        log.info("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
        stop.stop();

        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }



}