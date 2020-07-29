package com.shengsu.mq.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Bell on 2019/10/25.
 */
@Data
public class JpushSchedule {
    private JpushMessage Message;
    private List<String> aliasList;
    private Date date;
    private String MsgType;
    private String notificationTitle;
    private JpushExtrasparam extrasparam;
    private String content;
    private String name;
    private String msgId;
}
