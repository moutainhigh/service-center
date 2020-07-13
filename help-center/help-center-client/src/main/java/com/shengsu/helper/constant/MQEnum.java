package com.shengsu.helper.constant;


/**
 * Created by zyc on 2019/10/25.
 */
public enum MQEnum {
    //成功日志常量
    LOGBUSINESS("logBusinessTopic","logBusinessTag"),
    //失败日志常量
    LOGERROR("logErrorTopic","logErrorTag"),
    //极光推送常量
    JPUSHNORMAL("jpushMessageTopic","jpushNormalTag"),
    JPUSHSCHEDULE("jpushMessageTopic","jpushScheduleTag"),
    JPUSHSCHEDULECANCEL("jpushMessageTopic","jpushScheduleCancelTag"),
    //公众号推送
    ANY_WECHAT("wechatMessageTopic","wechatMessageTag"),
    //微信公众号支付通知-公众号
    WXPAY_NOTIFY_GZH("wxpayTopic","gzhNotifyTag"),
    //微信小程序支付通知-小程序
    WXPAY_NOTIFY_WEAPP("wxpayTopic","weappNotifyTag"),
    //支付宝支付通知
    ALIPAY_NOTIFY("alipayTopic","alipayNotifyTag"),
    //百度小程序支付通知
    BDPAY_NOTIFY("bdpayTopic","bdpayNofityTag"),
    //全文搜索Es数据消息通知
    ELASTICSEARCH("elasticsearchTopic","elasticsearchTag")
    ;

    private String topic;
    private String  tag;

    MQEnum(String topic, String tag) {
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
