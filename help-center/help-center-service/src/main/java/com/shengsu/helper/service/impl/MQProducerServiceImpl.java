package com.shengsu.helper.service.impl;


import com.shengsu.app.constant.ResultCode;
import com.shengsu.helper.constant.MQEnum;
import com.shengsu.helper.service.MQProducerService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by zyc on 2019/10/12.
 */
@Slf4j
@Service(value = "mqProducerService")
public class MQProducerServiceImpl implements MQProducerService {

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.producer.group}")
    private String group;
    private Integer sendMsgTimeout=5000;

    DefaultMQProducer producer;

    @PostConstruct
    public void init() {
        try {
            producer = new DefaultMQProducer(group);
            producer.setNamesrvAddr(namesrvAddr);
            producer.setSendMsgTimeout(sendMsgTimeout);
            producer.setVipChannelEnabled(false);
            producer.start();
        } catch (MQClientException e) {
            log.error("rocketMQ start error：", e);
        }
        log.info("rocketMQ is started !!");
    }

    @Override
    public ResultBean send(MQEnum producer, String body){
        return sendDelay(producer, body,0);
    }

    @Override
    public ResultBean sendDelay(MQEnum mqEnum, String body, int delayLevel) {
        try {
            Message message = new Message(mqEnum.getTopic(), mqEnum.getTag(), body.getBytes(RemotingHelper.DEFAULT_CHARSET));
            message.setDelayTimeLevel(delayLevel);
            producer.send(message);
        } catch (Exception e) {
            log.error("消息发送异常:", e);
            return ResultUtil.formResult(false, ResultCode.FAIL);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
}
