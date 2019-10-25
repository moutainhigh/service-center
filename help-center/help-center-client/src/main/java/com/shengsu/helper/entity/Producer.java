package com.shengsu.helper.entity;


/**
 * Created by zyc on 2019/10/25.
 */
public enum  Producer {
    //成功日志常量
    LOGBUSINESS("logBusinessTopic","logBusinessTag"),
    //失败日志常量
    LOGERROR("logErrorTopic","logErrorTag"),;


    private String topic;
    private String  tag;

    Producer(String topic, String tag) {
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
