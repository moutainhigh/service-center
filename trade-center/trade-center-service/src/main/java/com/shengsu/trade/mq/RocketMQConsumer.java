package com.shengsu.trade.mq;

import com.shengsu.mq.MessageListen;
import com.shengsu.trade.mq.service.BdpayNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by zyc on 2019/9/27.
 */
@Slf4j
@Component
public class RocketMQConsumer {
    @Autowired
    private BdpayNotifyService bdpayNotifyService;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.websiteGroup}")
    private String websiteGroup;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.bdpay.topic}")
    private String bdpayTopic;
    @Value("${rocketmq.consumer.bdpay.tag}")
    private String payNofityTag;
    DefaultMQPushConsumer consumer;

    @PostConstruct
    private void init() {
        consumer = new DefaultMQPushConsumer(websiteGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);

        //我们自己实现的监听类
        MessageListen messageListen = new MessageListen();
        messageListen.registerHandler(payNofityTag, bdpayNotifyService);
        consumer.registerMessageListener(messageListen);
        try {
            consumer.subscribe(bdpayTopic, payNofityTag);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.start();
            log.info("consume is start ,groupName:{},topic:{}", websiteGroup);
        } catch (MQClientException e) {
            log.error("consume start error:",e);
        }
    }

    @PreDestroy
    private void destroy() {
        if (consumer != null) {
            consumer.shutdown();
        }
    }
}
