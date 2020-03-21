package com.shengsu.any.wechat.service;


import com.shengsu.any.constant.TemplateMessageEnum;
import com.shengsu.any.wechat.entity.TempMessageData410928703;
import com.shengsu.result.ResultBean;

public interface TemplateMessageService {
    ResultBean pushTemplateMessage(String openId, TemplateMessageEnum templateMessageEnum, TempMessageData410928703 data);
}
