package com.shengsu.any.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.HttpClientUtil;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.constant.TemplateMessageEnum;
import com.shengsu.any.wechat.entity.TempMessageContent;
import com.shengsu.any.wechat.entity.TempMessageParamData;
import com.shengsu.any.wechat.entity.TemplateMessage;
import com.shengsu.any.wechat.service.TemplateMessageService;
import com.shengsu.result.ResultBean;
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
        ResultBean<Map<String, String>> accessTokenResult = getAccessToken();
        Map<String, String> accessTokenMap = null;
        if (accessTokenResult.isSuccess()) {
            accessTokenMap = accessTokenResult.getBody();
        }
        String accessToken = accessTokenMap.get("access_token");
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
    * @Description: 获取token
    * @Param: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    public ResultBean getAccessToken() {
        HashMap<String, String> resultMap = new HashMap<>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid",appID);
        params.put("secret",appSecret);
        params.put("grant_type","client_credential");
        String httpResultStr = HttpClientUtil.doGet(msgAccessTokenUrl, params);
        JSONObject jSONObject = JSON.parseObject(httpResultStr);
        if (jSONObject != null && jSONObject.get("errcode") != null) { // 有错误码
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_WECHAT_RESPONSE_ERROR);
        } else {
            String accessToken = String.valueOf(jSONObject.get("access_token"));
            resultMap.put("access_token", accessToken);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS,resultMap);
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
