package com.shengsu.website.constant;

/**
 * 消息模板
 */
public enum TemplateMessageEnum {
    // 预约成功提醒code
    MESSAGE_TEMPLATE_LAWKNOWLEDGE_SEND("IzSy_gQtdGqlKwpkUP1Z9-qCWyUN7FAZCGSvQ99QX6c");

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
