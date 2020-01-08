package com.shengsu.any.message.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 12:00
 **/
@Data
public class Message extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String messageId;// 消息id
    private String userId;// 用户id
    private String messageType;// 消息类型
    private String messageContent;// 消息内容
    private String messageState;// 消息状态
}
