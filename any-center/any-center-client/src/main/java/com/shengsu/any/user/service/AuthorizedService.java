package com.shengsu.any.user.service;

import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.result.ResultBean;

public interface AuthorizedService {
    String AUTHORIZATION_HEAD_KEY = "Authorization";

    String AUTHORIZATION_HEAD = "Bearer ";

    String generateToken(UserDetailsPo user);

    void destoryToken(String token);

    UserDetailsPo getUserByToken(String token);

    void flushUserToken(UserDetailsPo user, String token);

    ResultBean checkToken(String token);

    void logout(String token);
}
