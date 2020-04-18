package com.shengsu.trade.pay.service;

import com.shengsu.result.ResultBean;
import com.shengsu.trade.pay.nuomi.common.NuomiApiException;
import com.shengsu.trade.pay.vo.BaiduOrderVo;

import java.util.Map;

public interface BdpayService {
    ResultBean order(BaiduOrderVo baiduOrderVo) throws NuomiApiException;
    ResultBean checkSignWithRsa(Map<String, String> param,String rsaSign);
    String formatPaySubtype(String paySubtype);
}
