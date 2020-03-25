package com.shengsu.helper.constant;


/**
 * Created by zyc on 2019/10/25.
 */
public enum MQProducerEnum {
    //成功日志常量
    LOGBUSINESS("logGroup","logBusinessTopic","logBusinessTag"),
    //失败日志常量
    LOGERROR("logGroup","logErrorTopic","logErrorTag"),
    //极光推送常量
    JPUSHNORMAL("jpushGroup","jpushMessageTopic","jpushNormalTag"),
    JPUSHSCHEDULE("jpushGroup","jpushMessageTopic","jpushScheduleTag"),
    JPUSHSCHEDULECANCEL("jpushGroup","jpushMessageTopic","jpushScheduleCancelTag"),

    ANY_WECHAT("anyGroup","wechatMessageTopic","wechatMessageTag")
    ;

    private String group;
    private String topic;
    private String  tag;

    MQProducerEnum(String group,String topic, String tag) {
        this.group = group;
        this.topic = topic;
        this.tag = tag;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
