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
import static com.shengsu.website.app.constant.BizConst.SYSTEM_TAG_SHENGSU;
/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-09 09:50
 **/
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {
    @Value("${wechat.shengsu.accessLoginUrl}")
    private String ssAccessLoginUrl;
    @Value("${wechat.shengsu.appID}")
    private String ssAppID;
    @Value("${wechat.shengsu.appSecret}")
    private String ssAppsecret;

    @Value("${wechat.yuanshou.accessLoginUrl}")
    private String ysAccessLoginUrl;
    @Value("${wechat.yuanshou.appID}")
    private String ysAppID;
    @Value("${wechat.yuanshou.appSecret}")
    private String ysAppsecret;

    @Override
    public ResultBean authorize(WeChatVo weChatVo){
        String appId =SYSTEM_TAG_SHENGSU.equals(weChatVo.getSystemTag())?ssAppID:ysAppID;
        String secret = SYSTEM_TAG_SHENGSU.equals(weChatVo.getSystemTag())?ssAppsecret:ysAppsecret;
        String accessLoginUrl = SYSTEM_TAG_SHENGSU.equals(weChatVo.getSystemTag())?ssAccessLoginUrl:ysAccessLoginUrl;
        HashMap<String, String> resultMap = new HashMap<>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("js_code",weChatVo.getCode());
        params.put("grant_type","authorization_code");
        String httpResultStr = HttpClientUtil.doGet(accessLoginUrl, params);
        JSONObject jSONObject = JSON.parseObject(httpResultStr);
        String openid = String.valueOf(jSONObject.get("openid"));
        String sessionKey = String.valueOf(jSONObject.get("session_key"));
        resultMap.put("openid", openid);
        resultMap.put("session_key", sessionKey);
        return ResultUtil.formResult(true, ResultCode.SUCCESS,resultMap);
    }
}
