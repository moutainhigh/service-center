package com.shengsu.trade.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.trade.app.constant.ResultCode;
import com.shengsu.trade.app.util.HttpClientUtil;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.service.PayOrderService;
import com.shengsu.trade.pay.service.WxpayService;
import com.shengsu.trade.pay.util.PayOrderUtils;
import com.shengsu.trade.pay.vo.WxAppOrderVo;
import com.shengsu.trade.pay.vo.WxMwebOrderVo;
import com.shengsu.trade.pay.vo.WxOrderCancelVo;
import com.shengsu.trade.pay.vo.WxOrderVo;
import com.shengsu.trade.pay.wxsdk.MyConfig;
import com.shengsu.trade.pay.wxsdk.WXPay;
import com.shengsu.trade.pay.wxsdk.WXPayConstants;
import com.shengsu.trade.pay.wxsdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.shengsu.trade.app.constant.BizConst.*;

@Slf4j
@Service("wxpayService")
public class WxpayServiceImpl implements WxpayService {
    // 公众号
    @Value("${wxpay.gzh.appid}")
    private String gzhAppID ;
    @Value("${wxpay.mchid}")
    private String mchID;
    @Value("${wxpay.apikey}")
    private String apiKey;
    @Value("${wxpay.sandbox:false}")
    private boolean isSandbox;
    @Value("${wxpay.notifyUrl}")
    private String notifyUrl;
    // 小程序
    @Value("${wxpay.weapp.appid}")
    private String weAppID;
    // H5
    @Value("${wxpay.gzh.appid}")
    private String mwebAppID;

    @Autowired
    private CodeGeneratorService codeGeneratorService;
    @Autowired
    private PayOrderService payOrderService;

    /**
    * @Description: 微信公众号下单
    * @Param: * @Param wxOrderVo: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    @Override
    public ResultBean order(WxOrderVo wxOrderVo) throws Exception{
        log.info("开始下单");
        String accountId = wxOrderVo.getAccountId();
        int totalFee =  new BigDecimal(wxOrderVo.getAmount()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
        String outTradeNo = codeGeneratorService.generateCode("WGTN");
        //插入6位随机数
        outTradeNo=new StringBuilder(outTradeNo).insert(4,PayOrderUtils.randnum(6)).toString();
        // 配置微信请求参数
        log.info("配置微信请求参数");
        MyConfig config= getConfig("WG");
        WXPay wxpay = new WXPay(config, null, true, isSandbox);
        // 添加微信请求公共参数--返回预支付信息
        Map<String, String> reqData = getOrderRequsetData("案源王充值中心-会员充值",outTradeNo,String.valueOf(totalFee),wxOrderVo.getIpAddress(),notifyUrl,"JSAPI",wxOrderVo.getOpenId());
        // 构造返回值
        Map<String, String> resp = null;
        Map<String, String> result = null;
        try {
            // 请求微信返回预结果
            resp = wxpay.unifiedOrder(reqData);
            log.info("请求微信返回预期结果"+resp);
            String prepayId = resp.get("prepay_id");
            if ("SUCCESS".equals(resp.get("return_code"))&&"SUCCESS".equals(resp.get("result_code"))){
                // 返回前端数据
                result =  getOrderResponseData(resp,config,prepayId);
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
    /**
    * @Description: 微信小程序下单
    * @Param: * @Param wxAppOrderVo: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    @Override
    public ResultBean order(WxAppOrderVo wxAppOrderVo) throws Exception {
        log.info("开始下单");
        int totalFee =  new BigDecimal(wxAppOrderVo.getAmount()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
        String outTradeNo = codeGeneratorService.generateCode("WATN");
        //插入6位随机数
        outTradeNo=new StringBuilder(outTradeNo).insert(4,PayOrderUtils.randnum(6)).toString();
        // 配置微信请求参数
        log.info("配置微信请求参数");
        MyConfig config= getConfig("WA");
        WXPay wxpay = new WXPay(config, null, true, isSandbox);
        // 添加微信请求公共参数--返回预支付信息
        Map<String, String> reqData = getOrderRequsetData("小程序支付中心-支付",outTradeNo,String.valueOf(totalFee),wxAppOrderVo.getIpAddress(),notifyUrl,"JSAPI",wxAppOrderVo.getOpenId());
        // 返回数据
        Map<String, String> resp = null;
        Map<String, String> result = null;
        try {
            // 请求微信返回预结果
            resp = wxpay.unifiedOrder(reqData);
            log.info("请求微信返回预期结果"+resp);
            String prepayId = resp.get("prepay_id");
            if ("SUCCESS".equals(resp.get("return_code"))&&"SUCCESS".equals(resp.get("result_code"))){
                // 返回前端数据
                result =  getOrderResponseData(resp,config,prepayId);
                // order表生成订单数据
                PayOrder payOrder = PayOrderUtils.toPayOrder("",outTradeNo,prepayId,new BigDecimal(wxAppOrderVo.getAmount()),PAY_TYPE_WECHAT,ORDER_STATUS_UNPAID);
                payOrderService.create(payOrder);

            }
            return ResultUtil.formResult(true, ResultCode.SUCCESS,result);

        } catch (Exception e) {
            log.error("微信下单请求返回异常"+e);
            return ResultUtil.formResult(false, ResultCode.FAIL);
        }
    }
    /**
    * @Description: 微信H5下单
    * @Param: * @Param wxMwebOrderVo: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    @Override
    public ResultBean order(WxMwebOrderVo wxMwebOrderVo) throws Exception {
        log.info("开始下单");
        int totalFee =  new BigDecimal(wxMwebOrderVo.getAmount()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
        String outTradeNo = codeGeneratorService.generateCode("WMTN");
        //插入6位随机数
        outTradeNo=new StringBuilder(outTradeNo).insert(4,PayOrderUtils.randnum(6)).toString();
        // 配置微信请求参数
        log.info("配置微信请求参数");
        MyConfig config= getConfig("WM");
        WXPay wxpay = new WXPay(config, null, true, isSandbox);
        // 添加微信请求公共参数--返回预支付信息
        Map<String, String> reqData = getOrderRequsetData("订单支付-支付",outTradeNo,String.valueOf(totalFee),wxMwebOrderVo.getIpAddress(),notifyUrl,"MWEB","");
        fillSceneInfoData(reqData);
        // 返回数据
        Map<String, String> resp = null;
        Map<String, String> result = new HashMap<>();
        try {
            // 请求微信返回预结果
            resp = wxpay.unifiedOrder(reqData);
            log.info("请求微信返回预期结果"+resp);
            String prepayId = resp.get("prepay_id");
            String mwebUrl =  resp.get("mweb_url");
            if ("SUCCESS".equals(resp.get("return_code"))&&"SUCCESS".equals(resp.get("result_code"))){
                // 返回前端数据
                result.put("mwebUrl", mwebUrl);
                // order表生成订单数据
                PayOrder payOrder = PayOrderUtils.toPayOrder("",outTradeNo,prepayId,new BigDecimal(wxMwebOrderVo.getAmount()),PAY_TYPE_WECHAT,ORDER_STATUS_UNPAID);
                payOrderService.create(payOrder);

            }
            return ResultUtil.formResult(true, ResultCode.SUCCESS,result);

        } catch (Exception e) {
            log.error("微信下单请求返回异常"+e);
            return ResultUtil.formResult(false, ResultCode.FAIL);
        }
    }
    /**
    * @Description: 填充下单场景信息请求数据
    * @Param: * @Param reqData: 
    * @Return: * @return: void
    * @date: 
    */
    private void fillSceneInfoData(Map<String,String> reqData) {
        JSONObject scene_info = new JSONObject();
        JSONObject h5_info = new JSONObject();
        h5_info.put("type","Wap");
        h5_info.put("wap_url","https://pay.qq.com");
        h5_info.put("wap_name","订单支付");
        scene_info.put("h5_info",h5_info);
        reqData.put("scene_info",JSON.toJSONString(scene_info));
    }

    /**
    * @Description: 下单请求公共参数(允许添加非必填参数)
    * @Param: * @Param wxOrderVo: 
    * @Return: * @return: java.util.Map<java.lang.String,java.lang.String>
    * @date: 
    */
    private Map<String,String> getOrderRequsetData(String body,String outTradeNo,String totalFee,String ipAddress,String notifyUrl,String tradeType,String openid) {
        Map<String, String> data = new HashMap<>();
        data.put("body", body);
        data.put("out_trade_no", outTradeNo);
        data.put("total_fee",totalFee);
        data.put("spbill_create_ip", ipAddress);
        data.put("notify_url", notifyUrl);
        data.put("trade_type", tradeType);// 交易类型
        data.put("openid",openid);
        return data;
    }
    /**
    * @Description: 下单返回公共数据
    * @Param: 
    * @Return: * @return: java.util.Map<java.lang.String,java.lang.String>
    * @date: 
    */
    private Map<String,String> getOrderResponseData(Map<String, String> resp,MyConfig config,String prepayId) throws Exception {
        Map<String, String> result = new HashMap<>();
        result.put("appId", resp.get("appid"));
        result.put("timeStamp", String.valueOf(new Date().getTime()/1000));
        result.put("nonceStr", resp.get("nonce_str"));
        result.put("signType", "MD5");
        result.put("package", "prepay_id="+prepayId);
        String paySign = WXPayUtil.generateSignature(result, config.getKey(), WXPayConstants.SignType.MD5);
        result.put("paySign", paySign);
        return result;
    }

    @Override
    public ResultBean cancel(WxOrderCancelVo wxOrderCancelVo)throws Exception{
        String orderFlag = wxOrderCancelVo.getOrderNo().substring(0,2);
        MyConfig config = getConfig(orderFlag);
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
            log.error("异常",e);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS,resp);
    }

    @Override
    public ResultBean orderQuery(String outTradeNo)throws Exception{
        String orderFlag = outTradeNo.substring(0,2);
        MyConfig config =getConfig(orderFlag);
        WXPay wxpay = new WXPay(config, null, true, isSandbox);
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", outTradeNo);
        Map<String, String> resp = null;
        try {
            resp = wxpay.orderQuery(data);
        } catch (Exception e) {
            log.error("异常：",e);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS,resp);
    }

    @Override
    public MyConfig getConfig(String orderFlag) throws Exception{
        MyConfig config = new MyConfig();
        switch (orderFlag){
            case ORDER_FLAG_WECHAT_GZH:
                config.setAppID(gzhAppID);
                break;
            case ORDER_FLAG_WECHAT_WEAPP:
                config.setAppID(weAppID);
                break;
            case ORDER_FLAG_WECHAT_MWEB:
                config.setAppID(mwebAppID);
                break;
        }
        config.setMchID(mchID);
        config.setKey(isSandbox?getSignKey(mchID,apiKey):apiKey);
        return config;
    }
    private String getSignKey(String mchID,String apiKey) throws Exception {
        String nonce_str = WXPayUtil.generateNonceStr();//生成随机字符
        Map<String, String> param = new HashMap<>();
        param.put("mch_id", mchID);//需要真实商户号
        param.put("nonce_str", nonce_str);//随机字符
        String sign = WXPayUtil.generateSignature(param,apiKey,WXPayConstants.SignType.MD5);//通过SDK生成签名其中API_KEY为商户对应的真实密钥
        param.put("sign", sign);
        String xml = WXPayUtil.mapToXml(param);//将map转换为xml格式
        String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";//沙箱密钥获取api
        String SignKey = HttpClientUtil.sendPost(url, xml);
        Map<String, String> result = new HashMap<>();
        result = WXPayUtil.xmlToMap(SignKey);

        return result.get("sandbox_signkey");
    }
}
