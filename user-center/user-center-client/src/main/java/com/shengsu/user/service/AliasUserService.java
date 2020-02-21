package com.shengsu.user.service;


import com.shengsu.user.entity.AliasUser;

import java.util.List;

public interface AliasUserService extends UserService {
    List<AliasUser> getAliasUsers(List<String> userIds);
}
