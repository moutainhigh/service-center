package com.shengsu.mq;

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
    @Value("${rocketmq.consumer.jpush.topic}")
    private String jipushTopic;
    @Value("${rocketmq.consumer.jpush.tag}")
    private String jipushTag;
    @Value("${rocketmq.consumer.jpush.tag1}")
    private String jipushTag1;
    @Value("${rocketmq.consumer.jpush.tag2}")
    private String jipushTag2;

    @Bean
    public DefaultMQPushConsumer getsendToAliasListConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);


        MessageListen messageListen = new MessageListen();
        messageListen.registerHandler(jipushTag,jpushNormalService);
        messageListen.registerHandler(jipushTag1,jpushScheduleService);
        messageListen.registerHandler(jipushTag2, jpushScheduleCancelService);


        consumer.registerMessageListener(messageListen);
        try{
            consumer.subscribe(jipushTopic, jipushTag+"||"+jipushTag1+"||"+jipushTag2);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.start();

        }catch (MQClientException e){
            log.error("consumer start error");
            e.printStackTrace();
        }
        return consumer;
    }

}
