package com.shengsu.trade.pay.service;

import com.shengsu.result.ResultBean;
import com.shengsu.trade.pay.vo.WxOrderCancelVo;
import com.shengsu.trade.pay.vo.WxOrderVo;
import com.shengsu.trade.pay.wxsdk.MyConfig;


public interface WxpayService {
    ResultBean order(WxOrderVo wxOrderVo)throws Exception;
    ResultBean cancel(WxOrderCancelVo wxOrderCancelVo)throws Exception;
    MyConfig getMyConfig()throws Exception;
    ResultBean orderQuery(String outTradeNo)throws Exception;
}
