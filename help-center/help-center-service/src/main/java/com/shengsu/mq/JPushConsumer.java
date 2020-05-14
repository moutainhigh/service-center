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

/**
 * Created by Bell on 2019/10/24.
 */
@Slf4j
@Component
public class JPushConsumer extends AbstractMQConsumer{
    @Autowired
    private JpushNormalServiceImpl jpushNormalService;
    @Autowired
    private JpushScheduleServiceImpl jpushScheduleService;
    @Autowired
    private JpushScheduleCancelServiceImpl jpushScheduleCancelService;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.group}")
    private String jpushGroup;

    @Override
    @PostConstruct
    public void init() {
        try{
            consumer = new DefaultMQPushConsumer(jpushGroup);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.setVipChannelEnabled(false);
            consumer.setConsumeTimeout(consumeTimeout);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            registerMessageListener();
            subscribe();
            consumer.start();
        } catch (MQClientException e) {
            log.error("consumer start error:",e);
        }
    }

    @Override
    protected void registerMessageListener() {
        MessageListen messageListen = new MessageListen();
        String[] split = MQConsumerEnum.JPUSHMESSAGE.getTag().split("\\|\\|");
        messageListen.registerHandler(split[0], jpushNormalService);
        messageListen.registerHandler(split[1], jpushScheduleService);
        messageListen.registerHandler(split[2], jpushScheduleCancelService);
        consumer.registerMessageListener(messageListen);
    }

    @Override
    protected void subscribe() throws MQClientException {
        consumer.subscribe(MQConsumerEnum.JPUSHMESSAGE.getTopic(), MQConsumerEnum.JPUSHMESSAGE.getTag());
    }
}
