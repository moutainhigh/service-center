package com.shengsu.any.mq;

import com.shengsu.any.mq.service.TempMessageDataService;
import com.shengsu.any.mq.service.WxPayGzhNotifyService;
import com.shengsu.helper.constant.MQConsumerEnum;
import com.shengsu.mq.AbstractMQConsumer;
import com.shengsu.mq.MessageListen;
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
    private TempMessageDataService tempMessageDataService;
    @Autowired
    private WxPayGzhNotifyService wxPayGzhNotifyService;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.anyGroup}")
    private String anyGroup;

    @Override
    @PostConstruct
    public void init() {
        try {
            consumer = new DefaultMQPushConsumer(anyGroup);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.setVipChannelEnabled(false);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            registerMessageListener();
            subscribe();
            consumer.start();
            log.info("consume is start", anyGroup);
        } catch (MQClientException e) {
            log.error("consume start error:",e);
        }
    }

    @Override
    protected void registerMessageListener() {
        MessageListen messageListen = new MessageListen();
        messageListen.registerHandler(MQConsumerEnum.WECHATMESSAGE.getTag(), tempMessageDataService);
        messageListen.registerHandler(MQConsumerEnum.WXPAY_NOTIFY_GZH.getTag(), wxPayGzhNotifyService);
        consumer.registerMessageListener(messageListen);
    }

    @Override
    protected void subscribe() throws MQClientException {
        consumer.subscribe(MQConsumerEnum.WECHATMESSAGE.getTopic(), MQConsumerEnum.WECHATMESSAGE.getTag());
        consumer.subscribe(MQConsumerEnum.WXPAY_NOTIFY_GZH.getTopic(), MQConsumerEnum.WXPAY_NOTIFY_GZH.getTag());
    }
}
