package com.shengsu.user.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.user.entity.User;

public interface UserService extends BaseService<User,String> {
    public void transTest(User user);
}
