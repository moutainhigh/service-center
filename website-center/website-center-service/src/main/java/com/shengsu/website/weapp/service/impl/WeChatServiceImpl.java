package com.shengsu.website.weapp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.HttpClientUtil;
import com.shengsu.website.weapp.service.WeChatService;
import com.shengsu.website.weapp.vo.WeChatVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-09 09:50
 **/
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {
    @Value("${wechat.accessLoginUrl}")
    private String accessLoginUrl;
    @Value("${wechat.appID}")
    private String appID;
    @Value("${wechat.appSecret}")
    private String appsecret;
    @Override
    public ResultBean authorize(WeChatVo weChatVo){
        HashMap<String, String> resultMap = new HashMap<>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appID);
        params.put("secret", appsecret);
        params.put("js_code",weChatVo.getCode());
        params.put("grant_type","authorization_code");
        String httpResultStr = HttpClientUtil.doGet(accessLoginUrl, params);
        JSONObject jSONObject = JSON.parseObject(httpResultStr);
        if (jSONObject != null && jSONObject.get("errcode").toString() != "0") { // 有错误码
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_WECHAT_RESPONSE_ERROR);
        } else {
            String openid = String.valueOf(jSONObject.get("openid"));
            String sessionKey = String.valueOf(jSONObject.get("session_key"));
            String unionid = String.valueOf(jSONObject.get("unionid"));
            String errmsg = String.valueOf(jSONObject.get("errmsg"));
            resultMap.put("openid", openid);
            resultMap.put("session_key", sessionKey);
            resultMap.put("unionid", unionid);
            resultMap.put("errmsg", errmsg);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS,resultMap);
    }
}
