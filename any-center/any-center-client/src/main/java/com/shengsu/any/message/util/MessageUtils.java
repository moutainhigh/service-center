package com.shengsu.any.message.util;

import com.shengsu.any.message.entity.Message;

import java.util.UUID;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-15 20:38
 **/
public class MessageUtils {
    public static Message toMessage(String userId){
        Message message = new Message();
        message.setMessageId(UUID.randomUUID().toString());
        message.setUserId(userId);
        return message;
    }
}
