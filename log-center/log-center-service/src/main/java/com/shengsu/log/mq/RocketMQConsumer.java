package com.shengsu.log.mq;

import com.shengsu.helper.constant.MQEnum;
import com.shengsu.log.service.impl.LogBusinessServiceImpl;
import com.shengsu.log.service.impl.LogErrorServiceImpl;
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
    private LogBusinessServiceImpl logBusinessService;
    @Autowired
    private LogErrorServiceImpl logErrorService;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.group}")
    private String logGroup;

    @Override
    @PostConstruct
    public void init() {
        try {
            consumer = new DefaultMQPushConsumer(logGroup);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.setVipChannelEnabled(false);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            registerMessageListener();
            subscribe();
            consumer.start();
            log.info("consumer is start");
        } catch (MQClientException e) {
            log.error("consume start error:",e);
        }
    }

    @Override
    protected void registerMessageListener() {
        MessageListen messageListen = new MessageListen();
        messageListen.registerHandler(MQEnum.LOGBUSINESS.getTag(), logBusinessService);
        messageListen.registerHandler(MQEnum.LOGERROR.getTag(), logErrorService);
        consumer.registerMessageListener(messageListen);
    }

    @Override
    protected void subscribe() throws MQClientException{
        consumer.subscribe(MQEnum.LOGBUSINESS.getTopic(),MQEnum.LOGBUSINESS.getTag()+"||"+MQEnum.LOGERROR.getTag());
    }
}
