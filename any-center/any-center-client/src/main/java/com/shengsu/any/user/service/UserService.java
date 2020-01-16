package com.shengsu.any.user.service;

import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.vo.SmsSendVo;
import com.shengsu.any.user.vo.UserBandVo;
import com.shengsu.any.user.vo.UserLoginVo;
import com.shengsu.any.user.vo.UserEditVo;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-07 17:17
 **/
public interface UserService extends BaseService<User,String> {
    ResultBean sendSms(SmsSendVo smsSendVo);
    ResultBean login(UserLoginVo loginUserVo) throws IOException;
    ResultBean getUserBytoken(String token);
    User selectByWeChatOpenid(String openid);
    void supplyUserDetailsPo(UserDetailsPo userDetailsPo, User user);
    ResultBean band(UserBandVo userBandVo);
    ResultBean logout(String token);
    ResultBean edit(UserEditVo userEditVo);
    ResultBean toUserDetailsPos( List<User> users);
}
