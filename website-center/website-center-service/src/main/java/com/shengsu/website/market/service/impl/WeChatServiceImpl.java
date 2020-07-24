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
import java.util.List;
import java.util.Map;

import static com.shengsu.website.app.constant.BizConst.SYSTEM_TAG_SHENGSU;
/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-09 09:50
 **/
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {
    // 胜诉
    @Value("${wechat.shengsu.accessLoginUrl}")
    private String ssAccessLoginUrl;
    @Value("${wechat.shengsu.appID}")
    private String ssAppID;
    @Value("${wechat.shengsu.appSecret}")
    private String ssAppsecret;
    // 援手
    @Value("${wechat.yuanshou.accessLoginUrl}")
    private String ysAccessLoginUrl;
    @Value("${wechat.yuanshou.appID}")
    private String ysAppID;
    @Value("${wechat.yuanshou.appSecret}")
    private String ysAppsecret;
    // 援手公众号
    @Value("${wechat.yuanshou.gzh.appID}")
    private String ysGzhAppID;
    @Value("${wechat.yuanshou.gzh.appSecret}")
    private String ysGzhAppsecret;

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

    @Override
    public String getAccessToken() {
        String httpResultStr = HttpClientUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential" + "&appid=" + ysGzhAppID + "&secret=" + ysGzhAppsecret + "");
        JSONObject jSONObject = JSON.parseObject(httpResultStr);
        String accessToken = String.valueOf(jSONObject.get("access_token"));
        return accessToken;
    }

    @Override
    public List<String> getAllOpenId(String accessToken, String nextOpenid) {
        String httpResultStr = HttpClientUtil.sendGet("https://api.weixin.qq.com/cgi-bin/user/get", "&access_token=" + accessToken + "&next_openid=" + nextOpenid + "");
        Map<String,Object> jsonToMap = JSONObject.parseObject(httpResultStr);
        Map tmapMap = (Map) jsonToMap.get("data");
        List<String> result = (List<String>) tmapMap.get("openid");
        return result;
    }
}
