package com.shengsu.mq;

import com.shengsu.helper.constant.ConsumerEnum;
import com.shengsu.helper.service.impl.JpushNormalServiceImpl;
import com.shengsu.helper.service.impl.JpushScheduleCancelServiceImpl;
import com.shengsu.helper.service.impl.JpushScheduleServiceImpl;
import com.shengsu.mq.message.MessageListen;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by Bell on 2019/10/24.
 */
@Component
public class JPushConsumer {
    public static final Logger log = LoggerFactory.getLogger(JPushConsumer.class);
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
            consumer.subscribe(ConsumerEnum.JPUSHMESSAGE.getTopic(), ConsumerEnum.JPUSHMESSAGE.getTag());
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.start();

        } catch (MQClientException e) {
            log.error("consumer start error");
            e.printStackTrace();
        }
    }

    private void addJpushMessage(MessageListen messageListen) {
        String[] split = ConsumerEnum.JPUSHMESSAGE.getTag().split("\\|\\|");
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
