package com.shengsu.trade.pay.service.impl;

import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.result.ResultBean;
import com.shengsu.trade.app.constant.ResultCode;
import com.shengsu.trade.app.util.ResultUtil;
import com.shengsu.trade.pay.entity.BdBizInfo;
import com.shengsu.trade.pay.entity.BdOrderInfo;
import com.shengsu.trade.pay.nuomi.common.NuomiApiException;
import com.shengsu.trade.pay.nuomi.util.NuomiSignature;
import com.shengsu.trade.pay.service.BdPayService;
import com.shengsu.trade.pay.vo.BaiduOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-09 11:46
 **/
@Slf4j
@Service("bdPayService")
public class BdPayServiceImpl implements BdPayService {
    @Value("${bdpay.appKey}")
    private String appKey;
    @Value("${bdpay.dealId}")
    private String dealId;
    @Value("${bdpay.rsaPrivateKey}")
    private String rsaPrivateKey;
    @Autowired
    CodeGeneratorService codeGeneratorService;
    @Override
    public ResultBean order(BaiduOrderVo baiduOrderVo) throws NuomiApiException {
        String amount = baiduOrderVo.getAmount();
        String outTradeNo = codeGeneratorService.generateCode("BTN");
        //百度收银台创建
        BdOrderInfo orderInfo = new BdOrderInfo();
        orderInfo.setAppKey(appKey);
        orderInfo.setDealId(dealId);
        orderInfo.setDealTitle("百度小程序支付");
        orderInfo.setTotalAmount(amount);
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
        bizInfo.setTotalAmount(amount);
        bizInfo.setTpOrderId(outTradeNo);
        orderInfo.setBdBizInfo(bizInfo);
        //返回orderInfo
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("orderInfo",orderInfo);
        // TODO 生成订单
        return ResultUtil.formResult(false, ResultCode.SUCCESS,resultMap);
    }
}
