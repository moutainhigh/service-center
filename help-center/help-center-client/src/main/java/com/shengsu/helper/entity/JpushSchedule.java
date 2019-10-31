package com.shengsu.helper.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Bell on 2019/10/25.
 */
@Data
public class JpushSchedule {
    private Message Message;
    private List<String> aliasList;
    private Date date;
    private String MsgType;
    private String notificationTitle;
    private Extrasparam extrasparam;
    private String content;
    private String name;
    private String msgId;
}
