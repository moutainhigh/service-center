package com.shengsu.any.pay.service;

import com.shengsu.any.pay.vo.WxOrderCancelVo;
import com.shengsu.any.pay.vo.WxOrderQueryVo;
import com.shengsu.any.pay.vo.WxOrderVo;
import com.shengsu.any.pay.wxsdk.MyConfig;
import com.shengsu.result.ResultBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxpayService {
    ResultBean order(WxOrderVo wxOrderVo)throws Exception;
    ResultBean cancel(WxOrderCancelVo wxOrderCancelVo)throws Exception;
    MyConfig getMyConfig()throws Exception;
    ResultBean orderQuery(WxOrderQueryVo wxOrderQueryVo)throws Exception;
}
