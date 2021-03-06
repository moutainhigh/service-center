package com.shengsu.website.mq;

import com.shengsu.helper.constant.MQEnum;
import com.shengsu.mq.AbstractMQConsumer;
import com.shengsu.mq.MessageListen;
import com.shengsu.website.mq.service.EsLawKnowledgeService;
import com.shengsu.website.mq.service.EsNewsCenterService;
import com.shengsu.website.mq.service.PayNotifyService;
import com.shengsu.website.mq.service.TempMessageDataService;
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
    private PayNotifyService payNotifyService;
    @Autowired
    private EsLawKnowledgeService esLawKnowledgeService;
    @Autowired
    private EsNewsCenterService esNewsCenterService;

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
        messageListen.registerHandler(MQEnum.ALIPAY_NOTIFY.getTag(), payNotifyService);
        messageListen.registerHandler(MQEnum.WXPAY_NOTIFY_WEAPP.getTag(), payNotifyService);
        messageListen.registerHandler(MQEnum.BDPAY_NOTIFY.getTag(), payNotifyService);
        messageListen.registerHandler(MQEnum.LVSHIFU_WECHAT_MESSAGE.getTag(), tempMessageDataService);
        messageListen.registerHandler(MQEnum.ELASTICSEARCH_LAWKNOWLEDGE.getTag(), esLawKnowledgeService);
        messageListen.registerHandler(MQEnum.ELASTICSEARCH_NEWS_CENTER.getTag(), esNewsCenterService);
        consumer.registerMessageListener(messageListen);
    }

    @Override
    protected void subscribe() throws MQClientException {
        consumer.subscribe(MQEnum.ALIPAY_NOTIFY.getTopic(), MQEnum.ALIPAY_NOTIFY.getTag()+"||"+MQEnum.WXPAY_NOTIFY_WEAPP.getTag()+"||"+MQEnum.BDPAY_NOTIFY.getTag());
        consumer.subscribe(MQEnum.ELASTICSEARCH_LAWKNOWLEDGE.getTopic(),  MQEnum.ELASTICSEARCH_LAWKNOWLEDGE.getTag()+"||"+MQEnum.ELASTICSEARCH_NEWS_CENTER.getTag());
        consumer.subscribe(MQEnum.LVSHIFU_WECHAT_MESSAGE.getTopic(), MQEnum.LVSHIFU_WECHAT_MESSAGE.getTag());
    }
}
