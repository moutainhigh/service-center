package com.shengsu.trade.pay.service;

import com.shengsu.result.ResultBean;
import com.shengsu.trade.pay.nuomi.common.NuomiApiException;
import com.shengsu.trade.pay.vo.BaiduOrderVo;

public interface BdPayService {
    ResultBean order(BaiduOrderVo baiduOrderVo) throws NuomiApiException;
}
