package com.shengsu.log.mq;

import com.shengsu.log.mq.message.MessageListen;
import com.shengsu.log.service.impl.LogBusinessServiceImpl;
import com.shengsu.log.service.impl.LogErrorServiceImpl;
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
    private LogBusinessServiceImpl logBusinessService;
    @Autowired
    private LogErrorServiceImpl logErrorService;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.logGroup}")
    private String logGroup;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.logBusiness.topic}")
    private String logBusinessTopic;
    @Value("${rocketmq.consumer.logBusiness.tag}")
    private String logBusinessTag;
    @Value("${rocketmq.consumer.logError.topic}")
    private String logErrorTopic;
    @Value("${rocketmq.consumer.logError.tag}")
    private String logErrorTag;
    DefaultMQPushConsumer consumer;

    @PostConstruct
    private void init() {
        consumer = new DefaultMQPushConsumer(logGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);

        //我们自己实现的监听类
        MessageListen messageListen = new MessageListen();
        messageListen.registerHandler(logBusinessTag, logBusinessService);
        messageListen.registerHandler(logErrorTag, logErrorService);
        consumer.registerMessageListener(messageListen);
        try {
            consumer.subscribe(logBusinessTopic, logBusinessTag);
            consumer.subscribe(logErrorTopic, logErrorTag);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.start();
            log.info("consume is start ,groupName:{},topic:{}", logGroup);
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
