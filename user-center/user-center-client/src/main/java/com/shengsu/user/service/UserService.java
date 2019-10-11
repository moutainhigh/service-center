package com.shengsu.user.service;

import com.shengsu.app.exception.BizException;
import com.shengsu.base.service.BaseService;
import com.shengsu.user.entity.User;

import java.io.IOException;

public interface UserService extends BaseService<User,String> {
    int userExistCheck(User user);

    void create(User user) throws IOException;

    void assembleUserIconImageUrl(User user);

    void updatePwd(User user) throws IOException, BizException;

    void updateUser(User user) throws BizException;

}
