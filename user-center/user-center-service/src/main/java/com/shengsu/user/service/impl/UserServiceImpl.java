package com.shengsu.user.service.impl;

import com.shengsu.app.constant.ResultCode;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.user.entity.User;
import com.shengsu.user.mapper.UserMapper;
import com.shengsu.user.po.UserDetailsPo;
import com.shengsu.user.service.AuthorizedService;
import com.shengsu.user.service.UserService;
import com.shengsu.user.util.UserUtils;
import com.shengsu.user.vo.*;
import com.shengsu.util.EncryptUtil;
import com.shengsu.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OssService ossService;
    @Autowired
    private AuthorizedService authorizedService;

    @Override
    public BaseMapper<User, String> getBaseMapper() {
        return userMapper;
    }


    @Override
    public ResultBean listPage(User user) {
        Map<String, Object> map = new HashMap<>();
        user.setSearch(StringUtil.ToLikeStr(user.getSearch()));
        int totalCount = userMapper.countAll(user);
        if (totalCount > 0) {
            List<User> root = userMapper.listByPage(user);
            List<UserDetailsPo> userDetailsPos = UserUtils.toUserDetailsPos(root);
            map.put("root", userDetailsPos);
            map.put("totalCount", totalCount);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }

    @Override
    public ResultBean listByUserType(User user) {
        List<User> users = userMapper.listByUserType(user.getUserType());
        return ResultUtil.formResult(true, ResultCode.SUCCESS, users);
    }

    @Override
    public ResultBean create(UserCreateVo userCreateVo) throws IOException {
        String userName = userCreateVo.getUserName();
        User userNew = userMapper.selectbyUserName(userName);
        if (userNew != null) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_REGISTER_USER_EXISTED);
        }
        User user = UserUtils.toUser(userCreateVo);
        userMapper.save(user);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean edit(UserEditVo userEditVo) throws IOException {

        String userId = userEditVo.getUserId();
        User user = userMapper.get(userId);
        if (user == null) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_REGISTER_USER_NOT_EXISTED);
        }

        user = UserUtils.toUser(userEditVo);
        userMapper.update(user);
        String token = userEditVo.getToken();
        if (StringUtils.isNoneBlank(token)) {
            UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(user);
            userDetailsPo.setIconUrl(ossService.getUrl(OssConstant.OSS_USER_CENTERR_FFILEDIR, user.getIconOssResourceId()));
            authorizedService.flushUserToken(userDetailsPo, token);
        }


        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean updatePwd(UserUpdatePwdVo userUpdatePwdVo) throws IOException {

        String userId = userUpdatePwdVo.getUserId();
        User user = userMapper.get(userId);
        if (user == null) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_LOGIN_PARAM_ERROR);
        }

        String pwd = userUpdatePwdVo.getPwd();
        if (!(EncryptUtil.encryptMD5(pwd).equals(user.getPwd()))) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_USER_PASSWROD_DISAFFINITY);
        }
        String newPwd = userUpdatePwdVo.getNewPwd();
        user.setPwd(EncryptUtil.encryptMD5(newPwd));
        userMapper.update(user);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean updateEmail(UserUpdateEmailVo userUpdateEmailVo) {

        String userId = userUpdateEmailVo.getUserId();
        User user = userMapper.get(userId);
        if (user == null) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_LOGIN_PARAM_ERROR);
        }

        String email = userUpdateEmailVo.getEmail();
        user.setEmail(email);
        userMapper.update(user);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean login(UserLoginVo loginUser) throws IOException {


        User user = UserUtils.toUser(loginUser);
        user = userMapper.getOne(user);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_LOGIN_USERNAME_PASSWROD_ERROR);
        }

        UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(user);
        userDetailsPo.setIconUrl(ossService.getUrl(OssConstant.OSS_USER_CENTERR_FFILEDIR, user.getIconOssResourceId()));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", userDetailsPo);
        resultMap.put("token", authorizedService.generateToken(userDetailsPo));

        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    public ResultBean logout(String token) {
        if (token == null) {
            ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        authorizedService.destoryToken(token);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean getUserBytoken(String token) {
        if (token == null) {
            ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        UserDetailsPo user = authorizedService.getUserByToken(token);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", token);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
    }

    @Override
    public ResultBean updateRole(RoleEditVo roleEditVo) {
        String userId = roleEditVo.getUserId();
        User oldUser = userMapper.get(userId);
        if (oldUser == null) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_REGISTER_USER_NOT_EXISTED);
        }
        User user = UserUtils.toUser(roleEditVo);
        userMapper.updateRole(user);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
}
