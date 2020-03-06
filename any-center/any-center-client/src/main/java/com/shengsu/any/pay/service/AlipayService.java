package com.shengsu.any.pay.service;

import com.alipay.api.AlipayApiException;
import com.shengsu.any.pay.vo.AliOrderCancelVo;
import com.shengsu.any.pay.vo.AliOrderVo;
import com.shengsu.result.ResultBean;

import java.util.Map;

public interface AlipayService {
    String order(AliOrderVo aliOrderVo)throws Exception ;
    ResultBean cancel(AliOrderCancelVo aliOrderCancelVo)throws AlipayApiException;
    ResultBean orderQuery(String outTradeNo);
    boolean rsaCheckV1( Map<String,String> params)throws AlipayApiException;
}
