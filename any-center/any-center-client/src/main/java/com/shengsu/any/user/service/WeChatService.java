package com.shengsu.any.user.service;
import com.shengsu.any.user.vo.UserBandVo;
import com.shengsu.any.user.vo.ViewButtonVo;
import com.shengsu.any.user.vo.WeChatVo;
import com.shengsu.result.ResultBean;

public interface WeChatService {
    ResultBean pcLogin(WeChatVo weChatVo);
    ResultBean createView(ViewButtonVo viewButton);
    ResultBean band(UserBandVo userBandVo);
}
