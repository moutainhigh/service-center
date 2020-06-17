package com.shengsu.website.market.service;

import com.shengsu.result.ResultBean;
import com.shengsu.website.market.vo.AlipayVo;

public interface AlipayService {
    ResultBean authorize(AlipayVo alipayVo);
}
