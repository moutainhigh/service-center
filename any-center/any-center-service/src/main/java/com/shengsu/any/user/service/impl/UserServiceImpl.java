package com.shengsu.any.user.service.impl;

import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.mapper.UserMapper;
import com.shengsu.any.user.service.UserService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-07 17:20
 **/
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public BaseMapper<User, String> getBaseMapper() {
        return userMapper;
    }
}
