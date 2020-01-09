package com.shengsu.any.user.service;

import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.result.ResultBean;

public interface AuthorizedService {

    String generateToken(UserDetailsPo user);

    void destoryToken(String token);

    UserDetailsPo getUserByToken(String token);

    void flushUserToken(UserDetailsPo user, String token);

    ResultBean checkToken(String token);
}
