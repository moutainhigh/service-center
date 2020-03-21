package com.shengsu.any.constant;

/**
 * 消息模板
 */
public enum TemplateMessageEnum {
    MESSAGE_TEMPLATE_AUTHROTION_PASS("yRdcwKhNFdwfl85el7cjSiej6NSKS9bmT-rAcVZq_0w","www.baidu.com");

    private String key;
    private String value;

    TemplateMessageEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
