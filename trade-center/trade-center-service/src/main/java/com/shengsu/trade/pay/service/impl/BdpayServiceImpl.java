package com.shengsu.trade.pay.service.impl;

import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.result.ResultBean;
import com.shengsu.trade.app.constant.ResultCode;
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
}
