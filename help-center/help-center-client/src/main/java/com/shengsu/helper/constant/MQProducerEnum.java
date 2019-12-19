package com.shengsu.helper.constant;


/**
 * Created by zyc on 2019/10/25.
 */
public enum MQProducerEnum {
    //成功日志常量
    LOGBUSINESS("logBusinessTopic","logBusinessTag"),
    //失败日志常量
    LOGERROR("logErrorTopic","logErrorTag"),
    //极光推送常量
    JPUSHNORMAL("jpushMessageTopic","jpushNormalTag"),
    JPUSHSCHEDULE("jpushMessageTopic","jpushScheduleTag"),
    JPUSHSCHEDULECANCEL("jpushMessageTopic","jpushScheduleCancelTag"),;

    private String topic;
    private String  tag;

    MQProducerEnum(String topic, String tag) {
        this.topic = topic;
        this.tag = tag;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
