package com.shengsu.any.mq;

import com.shengsu.any.mq.service.TempMessageDataService;
import com.shengsu.mq.MessageListen;
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
    private TempMessageDataService tempMessageDataService;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.anyGroup}")
    private String anyGroup;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.wechatMessage.topic}")
    private String wechatMessageTopic;
    @Value("${rocketmq.consumer.wechatMessage.tag}")
    private String wechatMessageTag;
    DefaultMQPushConsumer consumer;

    @PostConstruct
    private void init() {
        consumer = new DefaultMQPushConsumer(anyGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);

        //我们自己实现的监听类
        MessageListen messageListen = new MessageListen();
        messageListen.registerHandler(wechatMessageTag, tempMessageDataService);
        consumer.registerMessageListener(messageListen);
        try {
            consumer.subscribe(wechatMessageTopic, wechatMessageTag);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.start();
            log.info("consume is start ,groupName:{},topic:{}", anyGroup);
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
