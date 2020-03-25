package com.shengsu.log.mq.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyc on 2019/9/27.
 */
@Slf4j
public class MessageListen implements MessageListenerConcurrently {

    //MessageProcessor接口的实现类放进map集合 key：tag value：MessageProcessor实习类
    private Map<String, MessageProcessor> handleMap = new HashMap<>();

    public void registerHandler(String tags, MessageProcessor messageProcessor) {
        handleMap.put(tags, messageProcessor);
    }
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        MessageExt ext = list.get(0);
        String message = null;
        try {
            message = new String(ext.getBody(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("异常",e);
        }
        //获取到tag
        String tags = ext.getTags();
        //根据tag从handleMap里获取到我们的处理类
        MessageProcessor messageProcessor = handleMap.get(tags);
        Object obj = null;

        try {
            //将String类型的message反序列化成对应的对象。
            obj = messageProcessor.transferMessage(message);
        } catch (Exception e) {
            log.error("反序列化失败了",e);
        }
        //处理消息
        boolean result = messageProcessor.handleMessage(obj);
        if (!result) {
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
