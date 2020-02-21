package com.shengsu.user.service.impl;

import com.shengsu.user.entity.AliasUser;
import com.shengsu.user.entity.User;
import com.shengsu.user.service.AliasUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * creator:zxh
 * 用户服务的别名服务、用于解决服务名称冲突问题
 */
@Service("aliasUserService")
public class AliasUserServiceImpl extends UserServiceImpl implements AliasUserService {
    @Override
    public List<AliasUser> getAliasUsers(List<String> userIds) {
        List<User> users = getMany(userIds);
        List<AliasUser> aliasUsers = new ArrayList<>();
        for(int i =0;i<users.size();i++) {
            AliasUser aliasUser = new AliasUser();
            aliasUser.setUserId(users.get(i).getUserId());
            aliasUser.setUserName(users.get(i).getUserName());
            aliasUser.setRealName(users.get(i).getRealName());
            aliasUser.setTel(users.get(i).getTel());
            aliasUsers.add(aliasUser);
        }
        return aliasUsers;
    }
}
