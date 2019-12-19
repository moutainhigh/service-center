package com.shengsu.helper.constant;

/**
 * Created by Bell on 2019/10/30.
 */
public enum MQConsumerEnum {

    JPUSHMESSAGE("jpushMessageTopic","jpushNormalTag||jpushScheduleTag||jpushScheduleCancelTag");
    private String topic;
    private String tag;

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
    MQConsumerEnum(String topic, String tag){
        this.topic=topic;
        this.tag=tag;
    }

}
