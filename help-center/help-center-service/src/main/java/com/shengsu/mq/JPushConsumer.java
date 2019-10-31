package com.shengsu.mq;

import com.shengsu.helper.constant.ConsumerEnum;
import com.shengsu.helper.service.impl.JpushScheduleServiceImpl;
import com.shengsu.mq.message.MessageListen;
import com.shengsu.helper.service.impl.JpushScheduleCancelServiceImpl;
import com.shengsu.helper.service.impl.JpushNormalServiceImpl;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;


    @Bean
    public DefaultMQPushConsumer getsendToAliasListConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);


        MessageListen messageListen = new MessageListen();
        addJpushMessage(messageListen);
        consumer.registerMessageListener(messageListen);
        try{
            consumer.subscribe(ConsumerEnum.JPUSHMESSAGE.getTopic(),ConsumerEnum.JPUSHMESSAGE.getTag());
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.start();

        }catch (MQClientException e){
            log.error("consumer start error");
            e.printStackTrace();
        }
        return consumer;
    }

    private void addJpushMessage(MessageListen messageListen){
        String[] split = ConsumerEnum.JPUSHMESSAGE.getTag().split("\\|\\|");
        messageListen.registerHandler(split[0],jpushNormalService);
        messageListen.registerHandler(split[1],jpushScheduleService);
        messageListen.registerHandler(split[2], jpushScheduleCancelService);
    }

}
