package com.shengsu.log.mq.message;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;

/**
 * Created by zyc on 2019/9/27.
 */
public interface MessageProcessor<T> {
    boolean handleMessage(T messageExt);

    Class<T> getClazz();

    default T transferMessage(String message) {
        T t = JSON.parseObject(message, getClazz());
        return JSON.parseObject(message, getClazz());
    }
}
