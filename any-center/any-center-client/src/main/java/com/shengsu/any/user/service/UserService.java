package com.shengsu.any.user.service;

import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.vo.SmsSendVo;
import com.shengsu.any.user.vo.UserBandVo;
import com.shengsu.any.user.vo.UserLoginVo;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

import java.io.IOException;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-07 17:17
 **/
public interface UserService extends BaseService<User,String> {
    ResultBean sendSms(SmsSendVo smsSendVo);
    ResultBean login(UserLoginVo loginUserVo) throws IOException;
    ResultBean getUserBytoken(String token);
    User selectByWeChatUnionId(String unionid);
    void supplyUserDetailsPo(UserDetailsPo userDetailsPo, User user);
    ResultBean band(UserBandVo userBandVo);
}
