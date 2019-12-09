package com.shengsu.log.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.DingTalkLink;
import com.shengsu.helper.service.DingTalkService;
import com.shengsu.log.entity.LogError;
import com.shengsu.log.mapper.LogErrorMapper;
import com.shengsu.log.mq.message.MessageProcessor;
import com.shengsu.log.service.LogErrorService;
import com.shengsu.log.util.LogErrorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zxh
 * @ClassName: LogErrorServiceImpl
 * @Description: 错误日志
 * @date 2018-9-10
 */
@Slf4j
@Service("logErrorService")
public class LogErrorServiceImpl extends BaseServiceImpl<LogError, String> implements LogErrorService, MessageProcessor<LogError> {

    /**
     * 错误日志数据访问对象
     */
    @Autowired
    private LogErrorMapper logErrorMapper;

    @Autowired
    private DingTalkService dingTalkService;
    @Value("${dingtalk.groupUrl}")
    private String groupUrl;
    @Value("${dingtalk.msgUrl}")
    private String msgUrl;
    @Value("${dingtalk.picUrl}")
    private String picUrl;

    @Override
    public BaseMapper<LogError, String> getBaseMapper() {
        return logErrorMapper;
    }

    @Override
    public void log(LogError logError) {
        logErrorMapper.save(logError);
    }

    @Override
    public boolean handleMessage(LogError logError) {
        log.info("处理消息：" + logError.toString());
        DingTalkLink dingTalkLink = new DingTalkLink();
        logErrorMapper.save(logError);
        //发送钉钉消息
        String environment1 = logError.getEnvironment();
        DingTalkLink dingTalkLink1 = LogErrorUtils.toDingTalkLink(logError, groupUrl, msgUrl, picUrl, environment1);
        dingTalkService.sendLink(dingTalkLink1);
        return true;
    }

    @Override
    public Class<LogError> getClazz() {
        return LogError.class;
    }
}