package com.shengsu.website.mq;

import com.shengsu.helper.constant.MQConsumerEnum;
import com.shengsu.mq.AbstractMQConsumer;
import com.shengsu.mq.MessageListen;
import com.shengsu.website.mq.service.PayNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by zyc on 2019/9/27.
 */
@Slf4j
@Component
public class RocketMQConsumer extends AbstractMQConsumer {
    @Autowired
    private PayNotifyService payNotifyService;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.group}")
    private String group;

    @Override
    @PostConstruct
    public void init() {
        try {
            consumer = new DefaultMQPushConsumer(group);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.setVipChannelEnabled(false);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            registerMessageListener();
            subscribe();
            consumer.start();
            log.info("consume is start", group);
        } catch (MQClientException e) {
            log.error("consume start error:",e);
        }
    }

    @Override
    protected void registerMessageListener() {
        MessageListen messageListen = new MessageListen();
        messageListen.registerHandler(MQConsumerEnum.ALIPAY_NOTIFY.getTag(), payNotifyService);
        consumer.registerMessageListener(messageListen);
    }

    @Override
    protected void subscribe() throws MQClientException {
        consumer.subscribe(MQConsumerEnum.ALIPAY_NOTIFY.getTopic(), MQConsumerEnum.ALIPAY_NOTIFY.getTag());
    }
}
