package com.shengsu.website.market.service;

import com.shengsu.result.ResultBean;
import com.shengsu.website.market.vo.WeChatVo;

import java.util.List;

public interface WeChatService {
    ResultBean authorize(WeChatVo weChatVo);
    String getAccessToken();

    List<String> getAllOpenId(String accessToken, String nextOpenid);
}
