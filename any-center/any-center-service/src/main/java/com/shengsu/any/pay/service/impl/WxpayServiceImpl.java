package com.shengsu.any.pay.service.impl;

import com.shengsu.any.account.entity.PayOrder;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.any.account.service.PayOrderService;
import com.shengsu.any.account.util.PayOrderUtils;
import com.shengsu.any.app.constant.BizConst;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.HttpClientUtil;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.pay.service.WxpayService;
import com.shengsu.any.pay.vo.WxOrderCancelVo;
import com.shengsu.any.pay.vo.WxOrderQueryVo;
import com.shengsu.any.pay.vo.WxOrderVo;
import com.shengsu.any.pay.wxsdk.MyConfig;
import com.shengsu.any.pay.wxsdk.WXPay;
import com.shengsu.any.pay.wxsdk.WXPayConstants;
import com.shengsu.any.pay.wxsdk.WXPayUtil;
import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.result.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("wxpayService")
public class WxpayServiceImpl implements WxpayService,BizConst {
    @Value("${wxpay.appid}")
    private String appID;
    @Value("${wxpay.mchid}")
    private String mchID;
    @Value("${wxpay.sandbox}")
    private boolean isSandbox;
    @Value("${wxpay.apikey}")
    private String apiKey;
    @Value("${wxpay.notifyUrl}")
    private String notifyUrl;
    @Autowired
    private CodeGeneratorService codeGeneratorService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private AccountServcie accountServcie;
    @Override
    public ResultBean order(WxOrderVo wxOrderVo) throws Exception{
//        //页面获取openId接口
//        String getopenid_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("appid",pcAppID);
//        params.put("secret",pcAppsecret);
//        params.put("code",code);
//        params.put("grant_type","authorization_code");
//        //向微信服务器发送get请求获取openIdStr
//        String openIdStr = HttpUtils.sendGet(getopenid_url, params);
//        JSONObject json = JSONObject.parseObject(openIdStr);//转成Json格式
//        String openId = json.getString("openid");//获取openId

        log.info("开始下单");
        String accountId = wxOrderVo.getAccountId();
        int totalFee =  new BigDecimal(wxOrderVo.getAmount()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
        String outTradeNo = codeGeneratorService.generateCode("WTN");
        String prepayId;
        // 配置微信请求参数
        log.info("配置微信请求参数");
        MyConfig config = new MyConfig();
        config.setAppID(appID);
        config.setMchID(mchID);
        config.setKey(isSandbox?getSignKey():apiKey);
        WXPay wxpay = new WXPay(config, null, true, isSandbox);
        // 添加微信请求参数--返回预支付信息
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "案源王充值中心-会员充值");
        data.put("out_trade_no", outTradeNo);
        data.put("total_fee",String.valueOf(totalFee));
        data.put("spbill_create_ip", wxOrderVo.getIpAddress());
        data.put("notify_url", notifyUrl);
        data.put("trade_type", "JSAPI");// 公众号支付
        data.put("openid",wxOrderVo.getOpenId());
        Map<String, String> resp = null;
        Map<String, String> result = new HashMap<String, String>();
        try {
            // 请求微信返回预结果
            resp = wxpay.unifiedOrder(data);
            log.info("请求微信返回预期结果"+resp);
            prepayId = resp.get("prepay_id");
            if ("SUCCESS".equals(resp.get("return_code"))&&"SUCCESS".equals(resp.get("result_code"))){
                // 返回前端数据
                result.put("appId", resp.get("appid"));
                result.put("timeStamp", String.valueOf(new Date().getTime()/1000));
                result.put("nonceStr", resp.get("nonce_str"));
                result.put("signType", "MD5");
                result.put("package", "prepay_id="+prepayId);
                String paySign = WXPayUtil.generateSignature(result, config.getKey(), WXPayConstants.SignType.MD5);
                result.put("paySign", paySign);

                // order表生成订单数据
                PayOrder payOrder = PayOrderUtils.toPayOrder(accountId,outTradeNo,prepayId,new BigDecimal(wxOrderVo.getAmount()),PAY_TYPE_WECHAT,ORDER_STATUS_UNPAID);
                payOrderService.create(payOrder);

            }
            return ResultUtil.formResult(true, ResultCode.SUCCESS,result);

        } catch (Exception e) {
            log.error("微信下单请求返回异常"+e);
            return ResultUtil.formResult(false, ResultCode.FAIL);
        }

    }

    @Override
    public ResultBean cancel(WxOrderCancelVo wxOrderCancelVo)throws Exception{
        MyConfig config = new MyConfig();
        config.setAppID(appID);
        config.setMchID(mchID);
        config.setKey(isSandbox?getSignKey():apiKey);
        WXPay wxpay = new WXPay(config, null, true, isSandbox);
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", wxOrderCancelVo.getOrderNo());
        Map<String, String> resp = null;
        try {
            // 微信订单关闭
            resp = wxpay.closeOrder(data);
            // order 表订单关闭
            PayOrder payOrder = new PayOrder();
            payOrder.setOrderNo(wxOrderCancelVo.getOrderNo());
            payOrder.setStatus(ORDER_STATUS_CLOSED);
            payOrderService.updateOrder(payOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS,resp);
    }

    @Override
    public ResultBean orderQuery(WxOrderQueryVo wxOrderQueryVo)throws Exception{
        MyConfig config = new MyConfig();
        config.setAppID(appID);
        config.setMchID(mchID);
        config.setKey(isSandbox?getSignKey():apiKey);
        WXPay wxpay = new WXPay(config, null, true, isSandbox);
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", wxOrderQueryVo.getOrderNo());
        Map<String, String> resp = null;
        try {
            resp = wxpay.orderQuery(data);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS,resp);
    }

    @Override
    public MyConfig getMyConfig() throws Exception{
        MyConfig config = new MyConfig();
        config.setAppID(appID);
        config.setMchID(mchID);
        config.setKey(isSandbox?getSignKey():apiKey);
        return config;
    }

    private String getSignKey() throws Exception {
        String nonce_str = WXPayUtil.generateNonceStr();//生成随机字符
        Map<String, String> param = new HashMap<String, String>();
        param.put("mch_id", mchID);//需要真实商户号
        param.put("nonce_str", nonce_str);//随机字符
        String sign = WXPayUtil.generateSignature(param,apiKey,WXPayConstants.SignType.MD5);//通过SDK生成签名其中API_KEY为商户对应的真实密钥
        param.put("sign", sign);
        String xml = WXPayUtil.mapToXml(param);//将map转换为xml格式
        String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";//沙箱密钥获取api
        String SignKey = HttpClientUtil.sendPost(url, xml);
        Map<String, String> result = new HashMap<String, String>();
        result = WXPayUtil.xmlToMap(SignKey);

        return result.get("sandbox_signkey");
    }
}
