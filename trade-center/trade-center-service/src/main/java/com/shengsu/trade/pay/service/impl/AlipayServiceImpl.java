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
import com.shengsu.trade.app.constant.ResultCode;
import com.shengsu.trade.app.util.ResultUtil;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.service.AlipayService;
import com.shengsu.trade.pay.service.PayOrderService;
import com.shengsu.trade.pay.util.PayOrderUtils;
import com.shengsu.trade.pay.vo.AliOrderCancelVo;
import com.shengsu.trade.pay.vo.AliOrderVo;
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
    @Value("${alipay.appid}")
    private String appID;
    @Value("${alipay.rsaPrivateKey}")
    private String rsaPrivateKey;
    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;
    @Value("${alipay.returnUrl}")
    private String returnUrl;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.gatewayUrl}")
    private String gatewayUrl;
    @Value("${alipay.signType}")
    private String signType;
    @Value("${alipay.timeoutExpress}")
    private String timeoutExpress;
    @Value("${alipay.productCode}")
    private String productCode;
    @Autowired
    CodeGeneratorService codeGeneratorService;
    @Autowired
    private PayOrderService payOrderService;
    @Override
    public String order(AliOrderVo aliOrderVo)throws Exception {
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(gatewayUrl, appID, rsaPrivateKey, "json", "UTF-8", alipayPublicKey,signType);
        AlipayTradeWapPayRequest alipayRequest=new AlipayTradeWapPayRequest();
        // 封装请求支付信息
        String outTradeNo = codeGeneratorService.generateCode("ATN");
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject("充值");
        model.setTotalAmount(String.valueOf(aliOrderVo.getAmount()));
        model.setBody("充值金额:"+aliOrderVo.getAmount());
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
        PayOrder payOrder = PayOrderUtils.toPayOrder(aliOrderVo.getAccountId(),outTradeNo,"",new BigDecimal(aliOrderVo.getAmount()),PAY_TYPE_ALIPAY,ORDER_STATUS_UNPAID);
        payOrderService.create(payOrder);
        return form;
    }

    @Override
    public ResultBean cancel(AliOrderCancelVo aliOrderCancelVo) throws AlipayApiException{
        //商户订单号和支付宝交易号不能同时为空。 trade_no、  out_trade_no如果同时存在优先取trade_no
        //商户订单号，和支付宝交易号二选一
        String outTradeNo = aliOrderCancelVo.getOrderNo();
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        AlipayClient client = new DefaultAlipayClient(gatewayUrl, appID, rsaPrivateKey, "json", "UTF-8", alipayPublicKey,signType);
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
        AlipayClient client = new DefaultAlipayClient(gatewayUrl, appID, rsaPrivateKey, "json", "UTF-8", alipayPublicKey,signType);
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
        return AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", signType);
    }

}
