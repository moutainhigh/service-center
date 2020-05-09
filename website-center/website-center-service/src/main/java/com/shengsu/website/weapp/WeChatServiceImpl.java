package com.shengsu.website.weapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.HttpClientUtil;
import com.shengsu.website.weapp.service.WeChatService;
import com.shengsu.website.weapp.vo.WeChatVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-09 09:50
 **/
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {
    @Value("${wechat.accessTokenUrl}")
    private String accessTokenUrl;
    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;
    @Value("${wechat.appID}")
    private String appID;
    @Value("${wechat.appSecret}")
    private String appsecret;
    @Override
    public ResultBean authorize(WeChatVo weChatVo){
        //获取微信用户授权信息
        Map<String, String> resultMap =  getWeChatUser(weChatVo).getBody();
        if(resultMap.isEmpty() || resultMap.size() == 0){
            return ResultUtil.formEmptyResult(true, ResultCode.EXCEPTION_WECHAT_RESPONSE_ERROR);
        }
        return ResultUtil.formResult(true,ResultCode.SUCCESS,resultMap);
    }

    public ResultBean<Map<String, String>> getWeChatUser(WeChatVo eChatVo) {
        String code = eChatVo.getCode();
        if(StringUtils.isBlank(code)){
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_WECHAT_LOGIN_CODE_IS_NULL);
        }
        // 获取微信token
        ResultBean<Map<String, String>>pcWXAccessTokenResult = getPcWXAccessToken(code);
        Map<String, String> pcWXAccessToken = null;
        if (pcWXAccessTokenResult.isSuccess()) {
            pcWXAccessToken = pcWXAccessTokenResult.getBody();
        }
        String accessToken = pcWXAccessToken.get("access_token");
        String openid = pcWXAccessToken.get("openid");

        if(StringUtils.isBlank(accessToken)|| StringUtils.isBlank(openid)){
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_PARAM_NULL,pcWXAccessToken);
        }
        // 获取微信用户信息
        Map<String, String> pcWeiXinUserInfo = getPcWeiXinUserInfo(openid, accessToken);
        String unionid = pcWeiXinUserInfo.get("unionid");
        if(StringUtils.isBlank(unionid)){
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_PARAM_NULL,pcWeiXinUserInfo);
        }

        pcWeiXinUserInfo.putAll(pcWeiXinUserInfo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS,pcWeiXinUserInfo);
    }

    /**
     * 获取getPcWXAccessToken（微信网站PC扫码）
     *
     */
    private ResultBean getPcWXAccessToken(String code) {
        HashMap<String, String> resultMap = new HashMap<>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appID);
        params.put("secret", appsecret);
        params.put("code",code);
        params.put("grant_type","authorization_code");
        String httpResultStr = HttpClientUtil.doGet(accessTokenUrl, params);
        JSONObject jSONObject = JSON.parseObject(httpResultStr);
        if (jSONObject != null && jSONObject.get("errcode") != null) { // 有错误码
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_WECHAT_RESPONSE_ERROR);
        } else {
            String accessToken = String.valueOf(jSONObject.get("access_token"));
            String refreshToken = String.valueOf(jSONObject.get("refresh_token"));
            String openid = String.valueOf(jSONObject.get("openid"));
            String expiresIn = String.valueOf(jSONObject.get("expires_in"));
            String unionid = String.valueOf(jSONObject.get("unionid"));
            resultMap.put("access_token", accessToken);
            resultMap.put("refresh_token", refreshToken);
            resultMap.put("openid", openid);
            resultMap.put("expires_in", expiresIn);
            resultMap.put("unionid", unionid);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS,resultMap);
    }

    /**
     * 获得微信用户信息（微信网站PC扫码）
     *
     * @param openId
     * @param accessToken
     * @return
     */
    private Map<String, String> getPcWeiXinUserInfo(String openId, String accessToken) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token",accessToken);
        params.put("openid",openId);
        params.put("lang","zh_CN");
        params.put("grant_type","authorization_code");
        String httpResultStr = HttpClientUtil.doGet(userInfoUrl, params);
        try {
            httpResultStr = new String(httpResultStr.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject jSONObject = JSON.parseObject(httpResultStr);
        if (jSONObject != null && jSONObject.get("errcode") != null) {
            String errcode = String.valueOf(jSONObject.get("errcode"));
            String errmsg = String.valueOf(jSONObject.get("errmsg"));
            resultMap.put("errmsg", errmsg);
            resultMap.put("errcode", errcode);
        } else {
            String nickname = String.valueOf(jSONObject.get("nickname"));
            String openid = String.valueOf(jSONObject.get("openid"));
            String sex = String.valueOf(jSONObject.get("sex"));
            String province = String.valueOf(jSONObject.get("province"));
            String city = String.valueOf(jSONObject.get("city"));
            String country = String.valueOf(jSONObject.get("country"));
            String headimgurl = String.valueOf(jSONObject.get("headimgurl"));
            String unionid = String.valueOf(jSONObject.get("unionid"));

            resultMap.put("nickname", nickname);
            resultMap.put("openid", openid);
            resultMap.put("sex", sex);
            resultMap.put("province", province);
            resultMap.put("city", city);
            resultMap.put("country", country);
            resultMap.put("headimgurl", headimgurl);
            resultMap.put("unionid", unionid);
        }
        return resultMap;
    }
}
