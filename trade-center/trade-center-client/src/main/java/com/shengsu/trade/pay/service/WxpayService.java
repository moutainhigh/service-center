package com.shengsu.trade.pay.service;

import com.shengsu.result.ResultBean;
import com.shengsu.trade.pay.vo.WxAppOrderVo;
import com.shengsu.trade.pay.vo.WxMwebOrderVo;
import com.shengsu.trade.pay.vo.WxOrderCancelVo;
import com.shengsu.trade.pay.vo.WxOrderVo;
import com.shengsu.trade.pay.wxsdk.MyConfig;


public interface WxpayService {
    ResultBean order(WxOrderVo wxOrderVo)throws Exception;
    ResultBean cancel(WxOrderCancelVo wxOrderCancelVo)throws Exception;
    ResultBean orderQuery(String outTradeNo)throws Exception;
    ResultBean order(WxAppOrderVo wxAppOrderVo)throws Exception;
    ResultBean order(WxMwebOrderVo wxMwebOrderVo)throws Exception;
    MyConfig getConfig(String orderFlag) throws Exception;
}
