package com.shengsu.mq;

import com.shengsu.helper.constant.MQConsumerEnum;
import com.shengsu.helper.service.impl.JpushNormalServiceImpl;
import com.shengsu.helper.service.impl.JpushScheduleCancelServiceImpl;
import com.shengsu.helper.service.impl.JpushScheduleServiceImpl;
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
 * Created by Bell on 2019/10/24.
 */
@Slf4j
@Component
public class JPushConsumer {
    @Autowired
    private JpushNormalServiceImpl jpushNormalService;
    @Autowired
    private JpushScheduleServiceImpl jpushScheduleService;
    @Autowired
    private JpushScheduleCancelServiceImpl jpushScheduleCancelService;
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.jpushGroup}")
    private String jpushGroup;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.consumeTimeout}")
    private int consumeTimeout;
    DefaultMQPushConsumer consumer;


    @PostConstruct
    public void init() {
        consumer = new DefaultMQPushConsumer(jpushGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeTimeout(consumeTimeout);
        MessageListen messageListen = new MessageListen();
        addJpushMessage(messageListen);
        consumer.registerMessageListener(messageListen);
        try {
            consumer.subscribe(MQConsumerEnum.JPUSHMESSAGE.getTopic(), MQConsumerEnum.JPUSHMESSAGE.getTag());
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.start();
        } catch (MQClientException e) {
            log.error("consumer start error:",e);
        }
    }

    private void addJpushMessage(MessageListen messageListen) {
        String[] split = MQConsumerEnum.JPUSHMESSAGE.getTag().split("\\|\\|");
        messageListen.registerHandler(split[0], jpushNormalService);
        messageListen.registerHandler(split[1], jpushScheduleService);
        messageListen.registerHandler(split[2], jpushScheduleCancelService);
    }

    @PreDestroy
    private void destroy() {
        if (consumer != null) {
            consumer.shutdown();
        }
    }

}
