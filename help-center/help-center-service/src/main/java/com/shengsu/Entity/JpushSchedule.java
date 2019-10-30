package com.shengsu.Entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Bell on 2019/10/25.
 */
@Data
public class JpushSchedule {
    Object obj;
    List<String> aliasList;
    Date date;
    String MsgType;
    String notificationTitle;
    String extrasparam;
    String content;
    String name;
    String msgId;
}
