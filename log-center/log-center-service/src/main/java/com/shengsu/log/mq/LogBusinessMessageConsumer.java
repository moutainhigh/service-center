package com.shengsu.log.mq;

import com.shengsu.log.entity.LogBusiness;
import com.shengsu.log.service.LogBusinessService;
import com.shengsu.log.service.LogErrorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.logging.inner.Logger;
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
@RocketMQMessageListener(topic = "logBusinessTopic", consumerGroup = "logGroup")
public class LogBusinessMessageConsumer implements RocketMQListener<LogBusiness> {
    @Autowired
    private LogBusinessService logBusinessService;

    @Override
    public void onMessage(LogBusiness business) {
        logBusinessService.save(business);
        System.out.println("[test--logs]**************************************"+business.toString());

    }
}
