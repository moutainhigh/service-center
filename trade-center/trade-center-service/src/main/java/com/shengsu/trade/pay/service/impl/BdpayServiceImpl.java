package com.shengsu.trade.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.result.ResultBean;
import com.shengsu.trade.app.constant.ResultCode;
import com.shengsu.trade.app.util.HttpClientUtil;
import com.shengsu.trade.app.util.ResultUtil;
import com.shengsu.trade.pay.entity.BdBizInfo;
import com.shengsu.trade.pay.entity.BdOrderInfo;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.nuomi.common.NuomiApiException;
import com.shengsu.trade.pay.nuomi.util.NuomiSignature;
import com.shengsu.trade.pay.service.BdpayService;
import com.shengsu.trade.pay.service.PayOrderService;
import com.shengsu.trade.pay.util.PayOrderUtils;
import com.shengsu.trade.pay.vo.BaiduOrderVo;
import com.shengsu.trade.pay.vo.BdPayNotifyVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static com.shengsu.trade.app.constant.BizConst.*;
/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-09 11:46
 **/
@Slf4j
@Service("bdpayService")
public class BdpayServiceImpl implements BdpayService{
    @Value("${bdpay.appKey}")
    private String appKey;
    @Value("${bdpay.appId}")
    private String appId;
    @Value("${bdpay.dealId}")
    private String dealId;
    @Value("${bdpay.rsaPrivateKey}")
    private String rsaPrivateKey;
    @Value("${bdpay.rsaPublicKey}")
    private String rsaPublicKey;
    @Autowired
    CodeGeneratorService codeGeneratorService;
    @Autowired
    private PayOrderService payOrderService;
    @Override
    public ResultBean order(BaiduOrderVo baiduOrderVo) throws NuomiApiException {
        String amount = baiduOrderVo.getAmount();
        String outTradeNo = codeGeneratorService.generateCode("BTN");
        //插入6位随机数
        outTradeNo=new StringBuilder(outTradeNo).insert(3,PayOrderUtils.randnum(6)).toString();
        int totalAmount =  new BigDecimal(amount).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
        //百度收银台创建
        BdOrderInfo orderInfo = new BdOrderInfo();
        orderInfo.setAppKey(appKey);
        orderInfo.setDealId(dealId);
        orderInfo.setDealTitle("百度小程序支付");
        orderInfo.setTotalAmount(String.valueOf(totalAmount));
        orderInfo.setTpOrderId(outTradeNo);
        orderInfo.setSignFieldsRange("1");

        //百度支付签名
        Map<String, String> data = new HashMap<>();
        data.put("appKey", orderInfo.getAppKey());
        data.put("dealId", dealId);
        data.put("totalAmount", orderInfo.getTotalAmount());
        data.put("tpOrderId", orderInfo.getTpOrderId());
        String sign = NuomiSignature.genSignWithRsa(data, rsaPrivateKey);
        if (StringUtils.isBlank(sign)) {
            log.error("签名错误");
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_BAIDU_SIGN_FAIL);
        }
        orderInfo.setRsaSign(sign);
        BdBizInfo bizInfo = new BdBizInfo();
        bizInfo.setAppKey(appKey);
        bizInfo.setDealld(dealId);
        bizInfo.setTotalAmount(String.valueOf(totalAmount));
        bizInfo.setTpOrderId(outTradeNo);
        orderInfo.setBdBizInfo(bizInfo);

        // order表生成订单数据
        PayOrder payOrder = PayOrderUtils.toPayOrder("",outTradeNo,"",new BigDecimal(amount),PAY_TYPE_BDPAY,ORDER_STATUS_UNPAID);
        payOrderService.create(payOrder);

        //返回orderInfo
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("orderInfo",orderInfo);
        log.info("下单参数：",JSON.toJSONString(orderInfo));
        return ResultUtil.formResult(true, ResultCode.SUCCESS,resultMap);
    }

    @Override
    public ResultBean checkSignWithRsa(Map<String, String> param, String rsaSign) {
        try {
            boolean checkSign =  NuomiSignature.checkSignWithRsa(param,rsaPublicKey,rsaSign);
            return  ResultUtil.formResult(true, ResultCode.SUCCESS,checkSign);
        } catch (NuomiApiException e) {
            log.info("验签异常：",e);
            return  ResultUtil.formResult(false, ResultCode.EXCEPTION_BAIDU_CHECK_SIGN_FAIL,false);
        }
    }

    @Override
    public String formatPaySubtype(String paySubtype) {
        // 支付渠道转化
        switch(paySubtype)
        {
            case "1117":
                paySubtype=PAY_TYPE_WECHAT;
                break;
            case "1087":
                paySubtype=PAY_TYPE_ALIPAY;
                break;
            case "1108":
                paySubtype=PAY_SUBTYPE_DUXIAOMAN;
                break;
            case "10004":
                paySubtype=PAY_SUBTYPE_BAIDU_FLASH_PAYMENT;
                break;
            case "1124":
                paySubtype=PAY_SUBTYPE_HUABEI;
                break;
            default:
                paySubtype="";
                break;
        }
        return paySubtype;
    }

    @Override
    public ResultBean orderQuery(String orderNo) throws NuomiApiException {
        String url = "https://dianshang.baidu.com/platform/entity/openapi/queryorderdetail";
        // 获取orderId
        PayOrder payOrder = payOrderService.getByOrderNo(orderNo);
        String orderId = "";
        String siteId = "";
        if (null !=payOrder){
            orderId=payOrder.getTransactionId();
            siteId =payOrder.getSiteId();
        }
        Map<String, String> data = new HashMap<>();
        data.put("appId", appId);
        data.put("appKey", appKey);
        data.put("orderId", orderId);
        data.put("siteId", siteId);
        data.put("sign", NuomiSignature.genSignWithRsa(data, rsaPrivateKey));
        String result = HttpClientUtil.doGet(url, data);
        return ResultUtil.formResult(true, ResultCode.SUCCESS,result);
    }

    @Override
    public String handleMessage(BdPayNotifyVo bdPayNotifyVo) {
        // 订单号
        String outTradeNo = bdPayNotifyVo.getOutTradeNo();
        // 状态
        String status = bdPayNotifyVo.getStatus();
        // 订单ID
        String orderId = bdPayNotifyVo.getOrderId() ;
        // 金额
        String totalMoney =bdPayNotifyVo.getTotalMoney();
        // 支付完成时间
        String payTime = bdPayNotifyVo.getPayTime();
        // 支付渠道
        String paySubtype = bdPayNotifyVo.getPaySubtype() ;
        // 支付渠道转化
        paySubtype = formatPaySubtype(paySubtype);
        // 用户id
        String siteId = bdPayNotifyVo.getSiteId() ;
        // 参数
        Map<String,Integer> consumedMap = bdPayNotifyVo.getConsumedMap();
        // 返回结果
        Map<String,Object> resultMap = bdPayNotifyVo.getResultMap();
        // 校验百度参数返回值
        if (BAIDU_ORDER_STATUS_PAID.equals(bdPayNotifyVo.getStatus())){
            log.info("支付状态=2: 支付成功");
            // 查询订单状态是否已经支付--如果已经支付不再提醒,百度支付需校验 tpOrderId(outTradeNo)商户号以及金额是实际金额
            PayOrder order = payOrderService.getByOrderNo(outTradeNo);
            log.info(JSON.toJSONString(order));
            BigDecimal amount = new BigDecimal(100).multiply(order.getAmount()).setScale(0);
            if (null !=order && ORDER_STATUS_UNPAID.equals(order.getStatus())&& totalMoney.equals(amount.toString())){
                log.info(JSON.toJSONString("修改订单状态"));
                // 修改订单-包括订单状态
                PayOrder payOrder = PayOrderUtils.toPayOrder(bdPayNotifyVo.getOutTradeNo(),ORDER_STATUS_PAID,orderId,payTime,paySubtype,siteId);
                payOrderService.updateOrder(payOrder);
            }
            resultMap.put("data",consumedMap);
            return JSON.toJSONString(resultMap);

        }else{
            //如处理支付回调的过程中开发者端参数异常、其他异常，返回以下参数进行异常退款
            log.error("支付失败");
            consumedMap.put("isErrorOrder",1);
            resultMap.put("data",consumedMap);
            return JSON.toJSONString(resultMap);
        }
    }
}
