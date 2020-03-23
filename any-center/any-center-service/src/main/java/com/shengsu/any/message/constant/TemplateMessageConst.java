package com.shengsu.any.message.constant;

public interface TemplateMessageConst {
    //推送消息字体颜色-灰色
    String TEMPLATE_MESSAGE_COLOR_D5D5D5= "#D5D5D5";
    //推送消息字体颜色-蓝色
    String TEMPLATE_MESSAGE_COLOR_0000FF= "#0000FF";

    // 认证通过
    //推送用户通过认证消息-First
    String TEMPLATE_MESSAGE_AUTH_PASS_FIRST_VALUE= "{0}律师您好！您已成为案源王合作律师";
    //推送用户通过认证消息-keyword1
    String TEMPLATE_MESSAGE_AUTH_PASS_KEYWORD1_VALUE = "认证通过";
    //推送用户认证通过-备注
    String TEMPLATE_MESSAGE_AUTH_PASS_REMARK_VALUE= "大量案源已更新，马上获取本地及时精准案源！";

    // 认证拒绝
    //推送用户认证拒绝消息-First
    String TEMPLATE_MESSAGE_AUTH_PASS_REJECT_VALUE= "{0}律师您好！您的资料审核不通过，具体原因请在线咨询客服。";
    //推送用户认证拒绝消息-keyword1
    String TEMPLATE_MESSAGE_AUTH_REJECT_KEYWORD1_VALUE = "认证失败";
    //推送用户认证拒绝-备注
    String TEMPLATE_MESSAGE_AUTH_REJECT_REMARK_VALUE= "大量案源已更新，马上获取本地及时精准案源！";

}
