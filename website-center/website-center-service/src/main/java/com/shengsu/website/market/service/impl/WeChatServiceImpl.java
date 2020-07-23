package com.shengsu.website.market.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.util.HttpClientUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.market.service.WeChatService;
import com.shengsu.website.market.vo.WeChatVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.shengsu.website.app.constant.BizConst.SYSTEM_TAG_LVSHIFU;
import static com.shengsu.website.app.constant.BizConst.SYSTEM_TAG_SHENGSU;
/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-09 09:50
 **/
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {
    @Value("${wechat.accessLoginUrl}")
    private String accessLoginUrl;
    // 胜诉
    @Value("${wechat.shengsu.appID}")
    private String ssAppID;
    @Value("${wechat.shengsu.appSecret}")
    private String ssAppsecret;
    // 援手
    @Value("${wechat.yuanshou.appID}")
    private String ysAppID;
    @Value("${wechat.yuanshou.appSecret}")
    private String ysAppsecret;
    //律师傅
    @Value("${wechat.lvshifu.appID}")
    private String lsfAppID;
    @Value("${wechat.lvshifu.appSecret}")
    private String lsfAppsecret;

    @Override
    public ResultBean authorize(WeChatVo weChatVo) {
        String appId = ysAppID;
        String secret = ysAppsecret;
        switch (weChatVo.getSystemTag()) {
            case SYSTEM_TAG_SHENGSU:
                appId = ssAppID;
                secret = ssAppsecret;
                break;
            case SYSTEM_TAG_LVSHIFU:
                appId = lsfAppID;
                secret = lsfAppsecret;
                break;
        }
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
