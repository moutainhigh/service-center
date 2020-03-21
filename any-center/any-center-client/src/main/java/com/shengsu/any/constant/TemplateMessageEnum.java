package com.shengsu.any.constant;

/**
 * 消息模板
 */
public enum TemplateMessageEnum {
    MESSAGE_TEMPLATE_AUTHROTION_PASS("yRdcwKhNFdwfl85el7cjSiej6NSKS9bmT-rAcVZq_0w");

    private String templateCode;

    TemplateMessageEnum(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}
