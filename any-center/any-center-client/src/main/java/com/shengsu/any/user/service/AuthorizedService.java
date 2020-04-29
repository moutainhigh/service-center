package com.shengsu.any.user.service;

import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.base.service.BaseAuthorizedService;
import com.shengsu.result.ResultBean;

public interface AuthorizedService extends BaseAuthorizedService {
    String generateToken(UserDetailsPo user);

    void destoryToken(String token);

    UserDetailsPo getUserByToken(String token);

    void flushUserToken(UserDetailsPo user, String token);

    ResultBean checkToken(String token);
}
