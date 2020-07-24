package com.shengsu.website.market.service;


import com.shengsu.result.ResultBean;
import com.shengsu.website.constant.TemplateMessageEnum;
import com.shengsu.website.market.entity.TempMessageParamData;

public interface TemplateMessageService {
    TempMessageParamData assembleTemplateDate(String firstValue, String keyWord1Value, String remarkValue);
    ResultBean pushTemplateMessage(String openId, String knowledgeId, TemplateMessageEnum templateMessageEnum, TempMessageParamData data);
}
