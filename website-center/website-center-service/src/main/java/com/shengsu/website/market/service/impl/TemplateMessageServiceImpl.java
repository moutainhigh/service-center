package com.shengsu.website.market.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.util.HttpClientUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.constant.TemplateMessageEnum;
import com.shengsu.website.market.entity.MiniprogramData;
import com.shengsu.website.market.entity.TempMessageContent;
import com.shengsu.website.market.entity.TempMessageParamData;
import com.shengsu.website.market.entity.TemplateMessage;
import com.shengsu.website.market.service.TemplateMessageService;
import com.shengsu.website.market.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.shengsu.website.constant.TemplateMessageConst.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 13:45
 **/
@Service("templateMessageService")
public class TemplateMessageServiceImpl implements TemplateMessageService {
    @Autowired
    private WeChatService weChatService;
    @Value("${wechat.yuanshou.appID}")
    private String ysAppID;
    @Value("${wechat.yuanshou.gzh.pagePath}")
    private String ysPagePath;

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

    @Override
    public ResultBean pushTemplateMessage(String openId, String knowledgeId, TemplateMessageEnum templateMessageEnum, TempMessageParamData data) {
        //创建消息发送实体对象
        TemplateMessage templateMessage=new TemplateMessage();
        templateMessage.setTemplate_id(templateMessageEnum.getTemplateCode());
        templateMessage.setTouser(openId);
        templateMessage.setData(data);
        MiniprogramData miniprogram = new MiniprogramData();
        miniprogram.setAppid(ysAppID);
        miniprogram.setPagepath(ysPagePath.replace("knowledgeId",knowledgeId));
        templateMessage.setMiniprogram(miniprogram);
        //将封装的数据转成JSON
        String jsonString = JSON.toJSONString(templateMessage);
        return pushMessage(jsonString);
    }
    private ResultBean pushMessage(String content) {
        // 获取token
        String accessToken = weChatService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
        //调用接口进行发送
        String result = HttpClientUtil.sendJsonStr(url, content);
        JSONObject json = JSONObject.parseObject(result);//转成Json格式
        String msgid = json.getString("msgid");//获取msgid
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("msgid",msgid);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }
}
