package com.shengsu.any.constant;

/**
 * 消息模板
 */
public enum TemplateMessageEnum {
    // 用户认证模板code
    MESSAGE_TEMPLATE_USER_AUTHROTION("yRdcwKhNFdwfl85el7cjSiej6NSKS9bmT-rAcVZq_0w"),
    // 预约成功提醒code
    MESSAGE_TEMPLATE_SUBSCRIBE_SUCCESS("O1haNf3At-fTvZ1n4ay27yF51KQQiBAix57ZUuvt9Ms");

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
