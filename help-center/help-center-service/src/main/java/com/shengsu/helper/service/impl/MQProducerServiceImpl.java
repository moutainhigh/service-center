package com.shengsu.helper.service.impl;


import com.shengsu.app.constant.ResultCode;
import com.shengsu.helper.constant.MQProducerEnum;
import com.shengsu.helper.service.MQProducerService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zyc on 2019/10/12.
 */
@Slf4j
@Service(value = "mqProducerService")
public class MQProducerServiceImpl implements MQProducerService {

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.producer.groups}")
    private String groups;
    private Integer maxMessageSize=131072;
    private Integer sendMsgTimeout=10000;

    static Map<String,DefaultMQProducer> producers = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        try {
            for(String group: Arrays.asList(groups.split(","))){
                DefaultMQProducer mqProducer = new DefaultMQProducer(group);
                mqProducer.setNamesrvAddr(namesrvAddr);
                mqProducer.setMaxMessageSize(maxMessageSize);
                mqProducer.setSendMsgTimeout(sendMsgTimeout);
                mqProducer.setVipChannelEnabled(false);
                mqProducer.start();
                producers.put(group,mqProducer);
            }
        } catch (MQClientException e) {
            log.error("rocketMQ start error：", e);
        }
        log.info("rocketMQ is started !!");
    }

    public ResultBean send(MQProducerEnum producer, String body){
        try {
            Message message = new Message(producer.getTopic(), producer.getTag(), body.getBytes(RemotingHelper.DEFAULT_CHARSET));
            producers.get(producer.getGroup()).send(message);
        } catch (Exception e) {
            log.error("消息发送异常:", e);
            return ResultUtil.formResult(false, ResultCode.FAIL);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
}
