package com.shengsu.helper.constant;


/**
 * Created by zyc on 2019/10/25.
 */
public enum MQProducerEnum {
    //成功日志常量
    LOGBUSINESS("helpGroup","logBusinessTopic","logBusinessTag"),
    //失败日志常量
    LOGERROR("helpGroup","logErrorTopic","logErrorTag"),
    //极光推送常量
    JPUSHNORMAL("helpGroup","jpushMessageTopic","jpushNormalTag"),
    JPUSHSCHEDULE("helpGroup","jpushMessageTopic","jpushScheduleTag"),
    JPUSHSCHEDULECANCEL("helpGroup","jpushMessageTopic","jpushScheduleCancelTag"),
    //公众号推送
    ANY_WECHAT("helpGroup","wechatMessageTopic","wechatMessageTag"),
    //微信公众号支付通知-公众号
    WXPAY_NOTIFY_GZH("helpGroup","wxpayTopic","gzhNotifyTag"),
    //微信小程序支付通知-小程序
    WXPAY_NOTIFY_WEAPP("helpGroup","wxpayTopic","weappNotifyTag"),
    //支付宝支付通知
    ALIPAY_NOTIFY("helpGroup","alipayTopic","alipayNotifyTag"),
    //百度小程序支付通知
    BDPAY_NOTIFY("helpGroup","bdpayTopic","bdpayNofityTag")
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
