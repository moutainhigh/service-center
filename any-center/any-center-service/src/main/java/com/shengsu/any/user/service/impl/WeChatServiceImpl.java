package com.shengsu.any.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.HttpClientUtil;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.any.user.service.UserService;
import com.shengsu.any.user.service.WeChatService;
import com.shengsu.any.user.util.UserUtils;
import com.shengsu.any.user.vo.WeChatVo;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-09 16:44
 **/
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {
    @Value("${wechat.pc.accessTokenUrl}")
    private String pcAccessTokenUrl;
    @Value("${wechat.pc.userInfoUrl}")
    private String pcUserInfoUrl;
    @Value("${wechat.pc.pcAppID}")
    private String pcAppID;
    @Value("${wechat.pc.pcAppsecret}")
    private String pcAppsecret;
    @Value("${wechat.code.expireTimeSecond}")
    private long WECHAT_CODE_INVALID_TIME;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorizedService authorizedService;
    @Autowired
    private OssService ossService;
    @Resource
    private RedisTemplate<Serializable,Serializable> redisTemplate;

    @Override
    public ResultBean pcLogin(WeChatVo weChatVo) {
        // 将微信用户信息放入缓存,保证前端页面重复刷新请求
        Map<String, String> resultMap = (HashMap<String, String>)redisTemplate.opsForValue().get(weChatVo.getCode());
        if (resultMap==null || resultMap.size()==0){
            resultMap =  getWeChatUser(weChatVo).getBody();
            redisTemplate.opsForValue().set(weChatVo.getCode(),new HashMap<>(resultMap), WECHAT_CODE_INVALID_TIME, TimeUnit.SECONDS);
        }
        // 根据 unionid 登录
        String unionid = resultMap.get("unionid");
        User user = userService.selectByWeChatUnionId(unionid);
        if(user == null){
            return ResultUtil.formResult(true,ResultCode.EXCEPTION_WECHAT_LOGIN_UNREGISTERED,resultMap);
        }
        // 登录到首页 返回token信息
        UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(user);
        userService.supplyUserDetailsPo(userDetailsPo,user);
        Map<String, Object> result = new HashMap<>();
        result.put("user", userDetailsPo);
        result.put("token", authorizedService.generateToken(userDetailsPo));
        return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
    }

    private User getUser(Map<String,String> resultMap) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRealName(resultMap.get("nickname"));
        user.setGender(Short.parseShort(resultMap.get("sex")));
        user.setWechatOpenid(resultMap.get("openid"));
        user.setWechatUnionid("unionid");
        return user;
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

        if(StringUtils.isBlank(accessToken)||StringUtils.isBlank(openid)){
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
        params.put("appid",pcAppID);
        params.put("secret",pcAppsecret);
        params.put("code",code);
        params.put("grant_type","authorization_code");
        String httpResultStr = HttpClientUtil.doGet(pcAccessTokenUrl, params);
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
        String httpResultStr = HttpClientUtil.doGet(pcUserInfoUrl, params);
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
