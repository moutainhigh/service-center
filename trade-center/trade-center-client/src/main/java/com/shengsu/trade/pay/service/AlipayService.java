package com.shengsu.trade.pay.service;

import com.alipay.api.AlipayApiException;
import com.shengsu.result.ResultBean;
import com.shengsu.trade.pay.vo.AliMarketOrderVo;
import com.shengsu.trade.pay.vo.AliOrderCancelVo;
import com.shengsu.trade.pay.vo.AliOrderVo;

import java.util.Map;

public interface AlipayService {
    String order(AliOrderVo aliOrderVo)throws Exception ;
    String order(AliMarketOrderVo aliMarketOrderVo)throws Exception ;
    ResultBean cancel(AliOrderCancelVo aliOrderCancelVo)throws AlipayApiException;
    ResultBean orderQuery(String outTradeNo);
    boolean rsaCheckV1(Map<String, String> params)throws AlipayApiException;
}
