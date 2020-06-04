package com.shengsu.trade.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.trade.app.constant.ResultCode;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.service.AlipayService;
import com.shengsu.trade.pay.service.PayOrderService;
import com.shengsu.trade.pay.util.PayOrderUtils;
import com.shengsu.trade.pay.vo.AliAnyOrderVo;
import com.shengsu.trade.pay.vo.AliMarketOrderVo;
import com.shengsu.trade.pay.vo.AliOrderCancelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import static com.shengsu.trade.app.constant.BizConst.*;

@Slf4j
@Service("alipayService")
public class AlipayServiceImpl implements AlipayService {
    @Value("${alipay.shengsu.appid}")
    private String ssAppID;
    @Value("${alipay.shengsu.rsaPrivateKey}")
    private String ssRsaPrivateKey;
    @Value("${alipay.shengsu.alipayPublicKey}")
    private String ssAlipayPublicKey;
    @Value("${alipay.shengsu.returnUrl.any}")
    private String anyReturnUrl;
    @Value("${alipay.shengsu.returnUrl.market}")
    private String ssMarketReturnUrl;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.gatewayUrl}")
    private String gatewayUrl;
    @Value("${alipay.signType:RSA2}")
    private String signType;
    @Value("${alipay.timeoutExpress:2m}")
    private String timeoutExpress;
    @Value("${alipay.productCode:QUICK_WAP_WAY}")
    private String productCode;

    // 援手
    @Value("${alipay.yuanshou.appid}")
    private String ysAppID;
    @Value("${alipay.yuanshou.rsaPrivateKey}")
    private String ysRsaPrivateKey;
    @Value("${alipay.yuanshou.alipayPublicKey}")
    private String ysAlipayPublicKey;
    @Value("${alipay.yuanshou.returnUrl.market}")
    private String ysMarketReturnUrl;

    @Autowired
    CodeGeneratorService codeGeneratorService;
    @Autowired
    private PayOrderService payOrderService;

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
        return getForm(aliAnyOrderVo.getAccountId(),outTradeNo,"充值","充值金额:",aliAnyOrderVo.getAmount(),anyReturnUrl);
    }
    /**
     * @Description: 市场推广H5下单
     * @Param: * @Param aliOrderVo:
     * @Return: * @return: java.lang.String
     * @date:
     */
    @Override
    public String order(AliMarketOrderVo aliMarketOrderVo) throws Exception {
        // 封装请求支付信息
        String outTradeNo = SYSTEM_TAG_YUANSHOU.equals(aliMarketOrderVo.getSystemTag())?codeGeneratorService.generateCode("YAMTN"):codeGeneratorService.generateCode("SAMTN");
        String returnUrl = SYSTEM_TAG_YUANSHOU.equals(aliMarketOrderVo.getSystemTag())?ysMarketReturnUrl:ssMarketReturnUrl;
        //插入6位随机数
        outTradeNo = new StringBuilder(outTradeNo).insert(5,PayOrderUtils.randnum(6)).toString();
        return getForm("",outTradeNo,"支付","支付金额:",aliMarketOrderVo.getAmount(),returnUrl+"?verifyCode="+aliMarketOrderVo.getVerifyCode());
    }
    /**
    * @Description: 获取支付下单返回的form表单数据
    * @Return: * @return: java.lang.String
    * @date: 
    */
    private String getForm(String accountId,String outTradeNo,String subject,String body, String amount,String returnUrl)throws Exception {
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        String orderFlag = outTradeNo.substring(0,3);
        AlipayClient client = getAlipayClient(orderFlag);
        AlipayTradeWapPayRequest alipayRequest=new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject(subject);
        model.setTotalAmount(amount);
        model.setBody(body+amount);
        model.setTimeoutExpress(timeoutExpress);
        model.setProductCode(productCode);
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
    * @Description: 获取AlipayClient
    * @Param: 
    * @Return: * @return: com.alipay.api.AlipayClient
    * @date: 
    */
    private AlipayClient getAlipayClient(String orderFlag){
        AlipayClient client = null;
        switch (orderFlag){
            case ORDER_FLAG_SHENGSU_ALIPAY_ANY_MWEB:
                client =new DefaultAlipayClient(gatewayUrl, ssAppID, ssRsaPrivateKey, "json", "UTF-8", ssAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_MWEB:
                client =new DefaultAlipayClient(gatewayUrl, ssAppID, ssRsaPrivateKey, "json", "UTF-8", ssAlipayPublicKey,signType);
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_MWEB:
                client =new DefaultAlipayClient(gatewayUrl, ysAppID, ysRsaPrivateKey, "json", "UTF-8", ysAlipayPublicKey,signType);
                break;
        }
       return client;
    }
    @Override
    public ResultBean cancel(AliOrderCancelVo aliOrderCancelVo) throws AlipayApiException{
        //商户订单号和支付宝交易号不能同时为空。 trade_no、  out_trade_no如果同时存在优先取trade_no
        //商户订单号，和支付宝交易号二选一
        String outTradeNo = aliOrderCancelVo.getOrderNo();
        String orderFlag = outTradeNo.substring(0,3);
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
        String orderFlag = outTradeNo.substring(0,3);
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
        String orderFlag = outTradeNo.substring(0,3);
        String publicKey = "";
        switch (orderFlag){
            case ORDER_FLAG_SHENGSU_ALIPAY_ANY_MWEB:
                publicKey = ssAlipayPublicKey;
                break;
            case ORDER_FLAG_SHENGSU_ALIPAY_MARKET_MWEB:
                publicKey = ssAlipayPublicKey;
                break;
            case ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_MWEB:
                publicKey = ysAlipayPublicKey;
                break;
        }
        return AlipaySignature.rsaCheckV1(params, publicKey, "UTF-8", signType);
    }

}
