package com.shengsu.website.market.service;

import com.shengsu.result.ResultBean;
import com.shengsu.website.market.vo.WeChatVo;

public interface WeChatService {
    ResultBean authorize(WeChatVo weChatVo);
}
