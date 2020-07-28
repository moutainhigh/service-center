package com.shengsu.any.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.constant.TemplateMessageEnum;
import com.shengsu.any.wechat.entity.TempMessageContent;
import com.shengsu.any.wechat.entity.TempMessageParamData;
import com.shengsu.any.wechat.entity.TemplateMessage;
import com.shengsu.any.wechat.service.TemplateMessageService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.util.HttpClientUtil;
import com.shengsu.util.WechatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.shengsu.any.wechat.constant.TemplateMessageConst.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 13:45
 **/
@Service("templateMessageService")
public class TemplateMessageServiceImpl implements TemplateMessageService {
    // 模板推送消息
    @Value("${wechat.templateMessage.appID}")
    private String appID;
    @Value("${wechat.templateMessage.templateMessageUrl}")
    private String templateMessageUrl;
    @Value("${wechat.templateMessage.appSecret}")
    private String appSecret;
    @Value("${wechat.templateMessage.accessTokenUrl}")
    private String msgAccessTokenUrl;
    @Value("${wechat.templateMessage.authDetailUrl}")
    private String authDetailUrl;

    @Override
    public ResultBean pushTemplateMessage(String openId, TemplateMessageEnum templateMessageEnum, TempMessageParamData data) {
        //创建消息发送实体对象
        TemplateMessage templateMessage=new TemplateMessage();
        templateMessage.setTemplate_id(templateMessageEnum.getTemplateCode());
        templateMessage.setTouser(openId);
        templateMessage.setUrl(authDetailUrl);
        templateMessage.setData(data);
        //将封装的数据转成JSON
        String jsonString = JSON.toJSONString(templateMessage);
        return pushMessage(jsonString);
    }

    private ResultBean pushMessage(String content) {
        //获得令牌
        String accessToken = WechatUtils.getAccessToken(appID,appSecret);
        String url = templateMessageUrl.replace("ACCESS_TOKEN", accessToken);
        //调用接口进行发送
        String result = HttpClientUtil.sendJsonStr(url, content);
        JSONObject json = JSONObject.parseObject(result);//转成Json格式
        String msgid = json.getString("msgid");//获取msgid
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("msgid",msgid);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }
    /**
     * @Description: 组装要发送的模板数据
     * @Param:
     * @date:
     */
    @Override
    public TempMessageParamData assembleTemplateDate(String firstValue, String keyWord1Value, String remarkValue) {
        //设置模板标题
        TempMessageContent first=new TempMessageContent(firstValue,TEMPLATE_MESSAGE_COLOR_D5D5D5);
        //设置模板内容
        TempMessageContent keyword1=new TempMessageContent(keyWord1Value,TEMPLATE_MESSAGE_COLOR_0000FF);
        //设置时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        TempMessageContent keyword2=new TempMessageContent(format,TEMPLATE_MESSAGE_COLOR_0000FF);
        //设置备注
        TempMessageContent remark=new TempMessageContent(remarkValue,TEMPLATE_MESSAGE_COLOR_0000FF);
        //创建模板信息数据对象
        TempMessageParamData data=new TempMessageParamData();
        data.setFirst(first);
        data.setKeyword1(keyword1);
        data.setKeyword2(keyword2);
        data.setRemark(remark);
        return data;
    }
}
