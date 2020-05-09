package com.shengsu.website.weapp.service;

import com.shengsu.result.ResultBean;
import com.shengsu.website.weapp.vo.WeChatVo;

public interface WeChatService {
    ResultBean authorize(WeChatVo weChatVo);
}
