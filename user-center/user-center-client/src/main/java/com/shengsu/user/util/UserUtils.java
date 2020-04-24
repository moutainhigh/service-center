package com.shengsu.user.util;

import com.shengsu.user.entity.User;
import com.shengsu.user.po.UserDetailsPo;
import com.shengsu.user.vo.*;
import com.shengsu.util.EncryptUtil;

import java.io.IOException;
import java.util.*;

/**
 * Created by zyc on 2019/8/26.
 */
public class UserUtils {
    public static User toUser(UserLoginVo userLoginVo) throws IOException {
        if (userLoginVo != null) {
            User user = new User();
            user.setPwd(EncryptUtil.encryptMD5(userLoginVo.getPwd()));
            user.setUserName(userLoginVo.getUserName());
            return user;
        }
        return null;
    }

    public static User toUser(UserCreateVo userCreateVo) throws IOException {
        if (userCreateVo != null) {
            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setPwd(EncryptUtil.encryptMD5(userCreateVo.getPwd()));
            user.setUserName(userCreateVo.getUserName());
            user.setRealName(userCreateVo.getRealName());
            user.setUserType(userCreateVo.getUserType());
            user.setDescri(userCreateVo.getDescri());
            user.setCreator(userCreateVo.getCreator());
            user.setMobile(userCreateVo.getMobile());
            user.setGender(userCreateVo.getGender());
            user.setTel(userCreateVo.getTel());
            user.setEmail(userCreateVo.getEmail());
            user.setIconOssResourceId(userCreateVo.getIconOssResourceId());
            user.setOrg(userCreateVo.getOrg());
            return user;
        }
        return null;
    }

    public static User toUser(UserEditVo userEditVo) throws IOException {
        if (userEditVo != null) {
            User user = new User();
            user.setUserId(userEditVo.getUserId());
            user.setUserName(userEditVo.getUserName());
            user.setRealName(userEditVo.getRealName());
            user.setUserType(userEditVo.getUserType());
            user.setDescri(userEditVo.getDescri());
            user.setCreator(userEditVo.getCreator());
            user.setMobile(userEditVo.getMobile());
            user.setGender(userEditVo.getGender());
            user.setTel(userEditVo.getTel());
            user.setEmail(userEditVo.getEmail());
            user.setIconOssResourceId(userEditVo.getIconOssResourceId());
            user.setOrg(userEditVo.getOrg());
            return user;
        }
        return null;
    }

    public static UserDetailsPo toUserDetailsPo(User user) {
        if (user != null) {
            UserDetailsPo detailsPo = new UserDetailsPo();
            detailsPo.setUserId(user.getUserId());
            detailsPo.setUserName(user.getUserName());
            detailsPo.setRealName(user.getRealName());
            detailsPo.setUserType(user.getUserType());
            detailsPo.setDescri(user.getDescri());
            detailsPo.setGender(user.getGender());
            detailsPo.setTel(user.getTel());
            detailsPo.setEmail(user.getEmail());
            detailsPo.setMobile(user.getMobile());
            detailsPo.setIconOssResourceId(user.getIconOssResourceId());
            detailsPo.setOrg(user.getOrg());
            detailsPo.setCreateTime(user.getCreateTime());
            return detailsPo;
        }
        return null;
    }


    public static List<UserDetailsPo> toUserDetailsPos(List<User> users) {
        if (users != null) {
            List<UserDetailsPo> userDetailsPos = new ArrayList<>();
            for (User user :
                    users) {
                userDetailsPos.add(toUserDetailsPo(user));
            }
            return userDetailsPos;
        }
        return null;
    }

    public static Map<String, User> toUserMap(List<User> users) {
        if (users != null) {
            Map<String, User> userMap = new HashMap<>();
            for (User user : users) {
                userMap.put(user.getUserId(), user);
            }
            return userMap;
        }
        return null;
    }
    public static User toUser(RoleEditVo roleEditVo) {
        if (roleEditVo != null) {
            User user = new User();
            user.setUserId(roleEditVo.getUserId());
            user.setUserType(roleEditVo.getUserType());
            return user;
        }
        return null;
    }
}
