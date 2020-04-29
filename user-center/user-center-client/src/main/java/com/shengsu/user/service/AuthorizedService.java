package com.shengsu.user.service;

import com.shengsu.base.service.BaseAuthorizedService;
import com.shengsu.user.po.UserDetailsPo;

/**
 * Created by zyc on 2019/10/15.
 */
public interface AuthorizedService extends BaseAuthorizedService {


    String generateToken(UserDetailsPo user);

    void destoryToken(String token);

    UserDetailsPo getUserByToken(String token);

    void flushUserToken(UserDetailsPo user,String token);
}
