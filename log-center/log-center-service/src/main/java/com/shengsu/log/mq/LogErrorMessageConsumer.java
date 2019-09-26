package com.shengsu.log.mq;

import com.shengsu.log.entity.LogBusiness;
import com.shengsu.log.entity.LogError;
import com.shengsu.log.service.LogBusinessService;
import com.shengsu.log.service.LogErrorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by zyc on 2019/9/24.
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "logErrorTopic", consumerGroup = "logGroup")
public class LogErrorMessageConsumer implements RocketMQListener<LogError> {

    @Autowired
    private LogErrorService logErrorService;

    @Override
    public void onMessage(LogError error) {
        logErrorService.save(error);
        System.out.println("[test--logs]**************************************"+error.toString());

    }
}
