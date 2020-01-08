package com.shengsu.any.message.service.impl;

import com.shengsu.any.message.entity.Message;
import com.shengsu.any.message.mapper.MessageMapper;
import com.shengsu.any.message.service.MessageService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 13:45
 **/
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Override
    public BaseMapper<Message, String> getBaseMapper() {
        return messageMapper;
    }
}
