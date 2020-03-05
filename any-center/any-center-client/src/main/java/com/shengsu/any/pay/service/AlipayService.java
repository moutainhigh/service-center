package com.shengsu.any.pay.service;

import com.alipay.api.AlipayApiException;
import com.shengsu.any.pay.vo.AliOrderCancelVo;
import com.shengsu.any.pay.vo.AliOrderVo;
import com.shengsu.result.ResultBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface AlipayService {
    String order(AliOrderVo aliOrderVo)throws Exception ;
    ResultBean cancel(AliOrderCancelVo aliOrderCancelVo)throws AlipayApiException;
    boolean rsaCheckV1( Map<String,String> params)throws AlipayApiException;
}
