package com.shengsu.any.mq;

import com.shengsu.any.mq.service.AliPayMwebNotifyService;
import com.shengsu.any.mq.service.TempMessageDataService;
import com.shengsu.any.mq.service.WxPayGzhNotifyService;
import com.shengsu.helper.constant.MQEnum;
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
    @Autowired
    private AliPayMwebNotifyService aliPayMwebNotifyService;

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
            consumer.setMaxReconsumeTimes(MAX_RECONSUME_TIMES);
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
        messageListen.registerHandler(MQEnum.ANY_WECHAT.getTag(), tempMessageDataService);
        messageListen.registerHandler(MQEnum.WXPAY_NOTIFY_GZH.getTag(), wxPayGzhNotifyService);
        messageListen.registerHandler(MQEnum.ALIPAY_NOTIFY.getTag(), aliPayMwebNotifyService);
        consumer.registerMessageListener(messageListen);
    }

    @Override
    protected void subscribe() throws MQClientException {
        consumer.subscribe(MQEnum.ANY_WECHAT.getTopic(), MQEnum.ANY_WECHAT.getTag());
        consumer.subscribe(MQEnum.WXPAY_NOTIFY_GZH.getTopic(), MQEnum.WXPAY_NOTIFY_GZH.getTag()+"||"+ MQEnum.ALIPAY_NOTIFY.getTag());
    }
}
