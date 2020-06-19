package com.shengsu.trade.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.helper.service.RedisService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.trade.app.constant.ResultCode;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.service.AlipayService;
import com.shengsu.trade.pay.service.PayOrderService;
import com.shengsu.trade.pay.util.PayOrderUtils;
import com.shengsu.trade.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.shengsu.trade.app.constant.BizConst.*;

@Slf4j
@Service("alipayService")
public class AlipayServiceImpl implements AlipayService {
    // 胜诉-h5
    @Value("${alipay.shengsu.h5.appid}")
    private String ssMwebAppID;
    @Value("${alipay.shengsu.returnUrl.any}")
    private String anyReturnUrl;
    @Value("${alipay.shengsu.returnUrl.market-h5}")
    private String ssMarketMwebReturnUrl;
    // 胜诉-小程序
    @Value("${alipay.shengsu.aliApp.appid}")
    private String ssAliAppID;
    // 胜诉公共部分
    @Value("${alipay.shengsu.rsaPrivateKey}")
    private String ssRsaPrivateKey;
    @Value("${alipay.shengsu.alipayPublicKey}")
    private String ssAlipayPublicKey;

    // 援手-h5
    @Value("${alipay.yuanshou.h5.appid}")
    private String ysMwebAppID;
    @Value("${alipay.yuanshou.returnUrl.market-h5}")
    private String ysMarketMwebReturnUrl;
    // 援手-小程序
    @Value("${alipay.yuanshou.aliApp.appid}")
    private String ysAliAppID;
    // 援手公共部分
    @Value("${alipay.yuanshou.rsaPrivateKey}")
    private String ysRsaPrivateKey;
    @Value("${alipay.yuanshou.alipayPublicKey}")
    private String ysAlipayPublicKey;

    // 援手胜诉公共部分
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.gatewayUrl}")
    private String gatewayUrl;
    @Value("${alipay.signType:RSA2}")
    private String signType;
    @Value("${alipay.timeoutExpress:2m}")
    private String timeoutExpress;
    @Value("${alipay.h5.productCode:QUICK_WAP_WAY}")
    private String mwebProductCode;
    @Value("${alipay.aliapp.productCode:QUICK_MSECURITY_PAY}")
    private String aliAppProductCode;
    // 缓存客户电话咨询中的客户电话,法律法域时效
    @Value("${telConsult.expireTimeSecond}")
    private long telConsultExpireTime;

    @Autowired
    CodeGeneratorService codeGeneratorService;
    @Autowired
    private PayOrderService payOrderService;
    @Resource
    private RedisService redisService;

    /**
    * @Description: 案源王H5下单
    * @Param: * @Param aliOrderVo: 
    * @Return: * @return: java.lang.String
    * @date: 
    */
    @Override
    public String order(AliAnyOrderVo aliAnyOrderVo)throws Exception {
        // 封装请求支付信息
        String outTradeNo = codeGeneratorService.generateCode("AATN");
        //插入6位随机数
        outTradeNo = new StringBuilder(outTradeNo).insert(4,PayOrderUtils.randnum(6)).toString();
        return getMwebForm(aliAnyOrderVo.getAccountId(),outTradeNo,"充值","充值金额:",aliAnyOrderVo.getAmount(),anyReturnUrl);
    }
    /**
     * @Description: (在线咨询)市场推广H5下单
     * @Param: * @Param aliOrderVo:
     * @Return: * @return: java.lang.String
     * @date:
     */
    @Override
    public String order(AliMarketOrderVo aliMarketOrderVo) throws Exception {
        // 封装请求支付信息
        String orderPrefixCode = SYSTEM_TAG_YUANSHOU.equals(aliMarketOrderVo.getSystemTag())?"YAMOTN":"SAMOTN";
        String outTradeNo = codeGeneratorService.generateCode(orderPrefixCode);
        //插入6位随机数
        outTradeNo = new StringBuilder(outTradeNo).insert(6,PayOrderUtils.randnum(6)).toString();
        String returnUrl = SYSTEM_TAG_YUANSHOU.equals(aliMarketOrderVo.getSystemTag())?ysMarketMwebReturnUrl:ssMarketMwebReturnUrl;
        return getMwebForm("",outTradeNo,"支付","支付金额:",aliMarketOrderVo.getAmount(),returnUrl+"?verifyCode="+aliMarketOrderVo.getVerifyCode());
    }
    /**
     * @Description: (电话咨询)市场推广H5下单
     * @Param: * @Param aliOrderVo:
     * @Return: * @return: java.lang.String
     * @date:
     */
    @Override
    public String order(AliMarketTelConsultOrderVo orderVo) throws Exception {
        // 封装请求支付信息
        String orderPrefixCode = SYSTEM_TAG_YUANSHOU.equals(orderVo.getSystemTag())?"YAMTTN":"SAMTTN";
        String outTradeNo = codeGeneratorService.generateCode(orderPrefixCode);
        //插入6位随机数
        outTradeNo = new StringBuilder(outTradeNo).insert(6,PayOrderUtils.randnum(6)).toString();
        // 将客户电话等数据存储到redis,时效是1小时
        setConsultDataToRedis(outTradeNo,orderVo.getTel(),orderVo.getLawField());
        String returnUrl = SYSTEM_TAG_YUANSHOU.equals(orderVo.getSystemTag())?ysMarketMwebReturnUrl:ssMarketMwebReturnUrl;
        return getMwebForm("",outTradeNo,"支付","支付金额:",orderVo.getAmount(),returnUrl);
    }
    /**
     * @Description: (在线咨询)市场推广小程序下单
     * @Param: * @Param aliOrderVo:
     * @Return: * @return: java.lang.String
     * @date:
     */
    @Override
    public ResultBean order(AliAppOrderVo aliAppOrderVo) throws Exception {
        // 封装请求支付信息
        String orderPrefixCode = SYSTEM_TAG_YUANSHOU.equals(aliAppOrderVo.getSystemTag())?"YAAOTN":"SAAOTN";
        String outTradeNo = codeGeneratorService.generateCode(orderPrefixCode);
        //插入6位随机数
        outTradeNo = new StringBuilder(outTradeNo).insert(6,PayOrderUtils.randnum(6)).toString();
        return getAppData(aliAppOrderVo.getBuyerId(),outTradeNo,"支付","支付金额:",aliAppOrderVo.getAmount());
    }
    /**
     * @Description: (电话咨询)市场推广小程序下单
     * @Param: * @Param aliOrderVo:
     * @Return: * @return: java.lang.String
     * @date:
     */
    @Override
    public ResultBean order(AliAppTelConsultOrderVo orderVo) throws Exception {
        // 封装请求支付信息
        String orderPrefixCode = SYSTEM_TAG_YUANSHOU.equals(orderVo.getSystemTag())?"YAATTN":"SAATTN";
        String outTradeNo = codeGeneratorService.generateCode(orderPrefixCode);
        //插入6位随机数
        outTradeNo = new StringBuilder(outTradeNo).insert(6,PayOrderUtils.randnum(6)).toString();
        // 将客户电话等数据存储到redis,时效是1小时
        setConsultDataToRedis(outTradeNo,orderVo.getTel(),orderVo.getLawField());
        return getAppData(orderVo.getBuyerId(),outTradeNo,"支付","支付金额:",orderVo.getAmount());
    }
    /**
    * @Description: 保存咨询数据到redis
    * @Param: * @Param tel: 
 * @Param lawField: 
    * @Return: * @return: void
    * @date: 
    */
    private void setConsultDataToRedis(String outTradeNo,String tel,String lawField){
        JSONObject param = new JSONObject();
        param.put("tel",tel);
        param.put("lawField",lawField);
        redisService.set(outTradeNo, JSON.toJSONString(param),telConsultExpireTime);
    }
    /**
    * @Description: 获取H5支付下单返回的form表单数据
    * @Return: * @return: java.lang.String
    * @date: 
    */
    private String getMwebForm(String accountId,String outTradeNo,String subject,String body, String amount,String returnUrl)throws Exception {
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        String orderFlag = outTradeNo.substring(0,4);
        AlipayClient client = getAlipayClient(orderFlag);
        AlipayTradeWapPayRequest alipayRequest=new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject(subject);
        model.setTotalAmount(amount);
        model.setBody(body+amount);
        model.setTimeoutExpress(timeoutExpress);
        model.setProductCode(mwebProductCode);
        alipayRequest.setBizModel(model);
        // 设置异步通知地址
        alipayRequest.setNotifyUrl(notifyUrl);
        // 设置同步地址
        alipayRequest.setReturnUrl(returnUrl);
        // 调用SDK生成表单
        String form = client.pageExecute(alipayRequest).getBody();
        // order表生成订单数据
        PayOrder payOrder = PayOrderUtils.toPayOrder(accountId,outTradeNo,"",new BigDecimal(amount),PAY_TYPE_ALIPAY,ORDER_STATUS_UNPAID);
        payOrderService.create(payOrder);
        return form;
    }
    /**
    * @Description: 小程序产生数据
    * @Return: * @return: java.lang.String
    * @date: 
    */
    private ResultBean getAppData(String buyerId,String outTradeNo,String subject,String body, String amount)throws Exception {
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        String orderFlag = outTradeNo.substring(0,4);
        AlipayClient client = getAlipayClient(orderFlag);
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeCreateRequest alipayRequest = new AlipayTradeCreateRequest();
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject(subject);
        model.setTotalAmount(amount);
        model.setBody(body+amount);
        model.setTimeoutExpress(timeoutExpress);
        model.setBuyerId(buyerId);
        alipayRequest.setBizModel(model);
        // 设置异步通知地址
        alipayRequest.setNotifyUrl(notifyUrl);
        // 调用SDK产生交易订单交易号数据
        String tradeNo = client.execute(alipayRequest).getTradeNo();
        // order表生成订单数据
        PayOrder payOrder = PayOrderUtils.toPayOrder("",outTradeNo,"",new BigDecimal(amount),PAY_TYPE_ALIPAY,ORDER_STATUS_UNPAID);
        payOrderService.create(payOrder);
        // 返回前端数据
        Map<String,String>map = new HashMap();
        map.put("tradeNo",tradeNo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS,map);
    }
    /**
    * @Description: 获取AlipayClient
    * @Param: 
    * @Return: * @return: com.alipay.api.AlipayClient
    * @date: 
    */
    private AlipayClient getAlipayClient(String orderFlag){
        AlipayClient client = null;
        switch (orderFlag){
            case ORDER_FLAG_SHENGSU_ALIPAY_ANY_MWEB:
                client =new DefaultAlipayClient(gatewayUrl, ssMwebAppID, ssRsaPrivateKey, "json", "UTF-8", ssAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_MWEB_ON_LINE:
                client =new DefaultAlipayClient(gatewayUrl, ssMwebAppID, ssRsaPrivateKey, "json", "UTF-8", ssAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_MWEB_ON_LINE:
                client =new DefaultAlipayClient(gatewayUrl, ysMwebAppID, ysRsaPrivateKey, "json", "UTF-8", ysAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_MWEB_TEL_CONSULT:
                client =new DefaultAlipayClient(gatewayUrl, ssMwebAppID, ssRsaPrivateKey, "json", "UTF-8", ssAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_MWEB_TEL_CONSULT:
                client =new DefaultAlipayClient(gatewayUrl, ysMwebAppID, ysRsaPrivateKey, "json", "UTF-8", ysAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_APP_ON_LINE:
                client =new DefaultAlipayClient(gatewayUrl, ssAliAppID, ssRsaPrivateKey, "json", "UTF-8", ssAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_APP_ON_LINE:
                client =new DefaultAlipayClient(gatewayUrl, ysAliAppID, ysRsaPrivateKey, "json", "UTF-8", ysAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_APP_TEL_CONSULT:
                client =new DefaultAlipayClient(gatewayUrl, ssAliAppID, ssRsaPrivateKey, "json", "UTF-8", ssAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_APP_TEL_CONSULT:
                client =new DefaultAlipayClient(gatewayUrl, ysAliAppID, ysRsaPrivateKey, "json", "UTF-8", ysAlipayPublicKey,signType);
                break;
        }
       return client;
    }
    @Override
    public ResultBean cancel(AliOrderCancelVo aliOrderCancelVo) throws AlipayApiException{
        //商户订单号和支付宝交易号不能同时为空。 trade_no、  out_trade_no如果同时存在优先取trade_no
        //商户订单号，和支付宝交易号二选一
        String outTradeNo = aliOrderCancelVo.getOrderNo();
        String orderFlag = outTradeNo.substring(0,4);
        AlipayClient client = getAlipayClient(orderFlag);
        AlipayTradeCloseRequest alipayRequest=new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model =new AlipayTradeCloseModel();
        model.setOutTradeNo(outTradeNo);
        alipayRequest.setBizModel(model);
        AlipayTradeCloseResponse alipayResponse = client.execute(alipayRequest);
        log.info(alipayResponse.getBody());
        // order 表订单关闭
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderNo(outTradeNo);
        payOrder.setStatus(ORDER_STATUS_CLOSED);
        payOrderService.updateOrder(payOrder);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean orderQuery(String outTradeNo){
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        String orderFlag = outTradeNo.substring(0,4);
        AlipayClient client = getAlipayClient(orderFlag);
        AlipayTradeQueryRequest alipayRequest=new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model =new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        alipayRequest.setBizModel(model);
        AlipayTradeQueryResponse alipayResponse = null;
        try {
            alipayResponse = client.execute(alipayRequest);
        } catch (AlipayApiException e) {
            log.error("异常",e);
            e.printStackTrace();
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS,alipayResponse);
    }

    @Override
    public boolean rsaCheckV1(Map<String, String> params) throws AlipayApiException{
        String outTradeNo = params.get("out_trade_no");
        String orderFlag = outTradeNo.substring(0,4);
        String publicKey = "";
        switch (orderFlag){
            case ORDER_FLAG_SHENGSU_ALIPAY_ANY_MWEB:
                publicKey = ssAlipayPublicKey;
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_MWEB_ON_LINE:
                publicKey = ssAlipayPublicKey;
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_MWEB_ON_LINE:
                publicKey = ysAlipayPublicKey;
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_MWEB_TEL_CONSULT:
                publicKey = ssAlipayPublicKey;
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_MWEB_TEL_CONSULT:
                publicKey = ysAlipayPublicKey;
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_APP_ON_LINE:
                publicKey = ssAlipayPublicKey;
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_APP_ON_LINE:
                publicKey = ysAlipayPublicKey;
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_APP_TEL_CONSULT:
                publicKey = ssAlipayPublicKey;
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_APP_TEL_CONSULT:
                publicKey = ysAlipayPublicKey;
                break;
        }
        return AlipaySignature.rsaCheckV1(params, publicKey, "UTF-8", signType);
    }

}
