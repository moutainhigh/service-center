package com.shengsu.any.wechat.service;


import com.shengsu.any.constant.TemplateMessageEnum;
import com.shengsu.any.wechat.entity.TempMessageParamData;
import com.shengsu.result.ResultBean;

public interface TemplateMessageService {
    TempMessageParamData assembleTemplateDate(String firstValue, String keyWord1Value, String remarkValue);
    ResultBean pushTemplateMessage(String openId, TemplateMessageEnum templateMessageEnum, TempMessageParamData data);
}
