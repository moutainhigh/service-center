package com.shengsu.website.market.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.market.service.AlipayService;
import com.shengsu.website.market.vo.AlipayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.shengsu.website.app.constant.BizConst.SYSTEM_TAG_SHENGSU;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-06-10 14:38
 **/
@Slf4j
@Service("alipayService")
public class AlipayServiceImpl implements AlipayService {
    @Value("${alipay.shengsu.accessLoginUrl}")
    private String ssAccessLoginUrl;
    @Value("${alipay.shengsu.appID}")
    private String ssAppID;
    @Value("${alipay.shengsu.privateKey}")
    private String ssPrivateKey;
    @Value("${alipay.shengsu.publicKey}")
    private String ssPublicKey;
    @Value("${alipay.yuanshou.appID}")
    private String ysAppID;
    @Value("${alipay.yuanshou.privateKey}")
    private String ysPrivateKey;
    @Value("${alipay.yuanshou.publicKey}")
    private String ysPublicKey;
    @Value("${alipay.yuanshou.accessLoginUrl}")
    private String ysAccessLoginUrl;
    @Override
    public ResultBean authorize(AlipayVo alipayVo) {
        AlipayClient alipayClient;
        if (SYSTEM_TAG_SHENGSU.equals(alipayVo.getSystemTag())) {
            alipayClient = new DefaultAlipayClient(ssAccessLoginUrl, ssAppID, ssPrivateKey, "json", "GBK", ssPublicKey, "RSA2");
        } else {
            alipayClient = new DefaultAlipayClient(ysAccessLoginUrl, ysAppID, ysPrivateKey, "json", "GBK", ysPublicKey, "RSA2");
        }
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(alipayVo.getCode());
        // request.setRefreshToken("201208134b203fe6c11548bcabd8da5bb087a83b");
        AlipaySystemOauthTokenResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //String accessToken = response.getAccessToken();
        if (response.isSuccess()) {
            log.info("支付宝用户唯一id：" + response.getUserId());
            return ResultUtil.formResult(true, ResultCode.SUCCESS,response.getUserId());
        }
        return ResultUtil.formResult(false, ResultCode.GET_EXCEPTION);
    }
}
