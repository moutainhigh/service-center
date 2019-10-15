package com.shengsu.user.service.impl;

import com.shengsu.app.constant.ResultBean;
import com.shengsu.app.constant.ResultCode;
import com.shengsu.app.util.ResultUtil;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.user.entity.User;
import com.shengsu.user.mapper.UserMapper;
import com.shengsu.user.po.UserDetailsPo;
import com.shengsu.user.service.UserService;
import com.shengsu.user.util.UserUtils;
import com.shengsu.user.vo.*;
import com.shengsu.util.EncryptUtil;
import com.shengsu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OssService ossService;

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
    public ResultBean create(UserCreateVo userCreateVo) throws IOException {
        String userName = userCreateVo.getUserName();
        User userNew = userMapper.selectbyUserName(userName);
        if (userNew != null) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_REGISTER_USER_EXISTED, null);
        }
        User user = UserUtils.toUser(userCreateVo);
        userMapper.save(user);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean<UserDetailsPo> edit(UserEditVo userEditVo) throws IOException {
        User user = UserUtils.toUser(userEditVo);
        userMapper.update(user);
        UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(user);
        userDetailsPo.setIconUrl(ossService.getUrl(OssConstant.OSS_USER_CENTERR_FFILEDIR,user.getIconOssResourceId()));
        return ResultUtil.formResult(true, ResultCode.SUCCESS, userDetailsPo);
    }

    @Override
    public ResultBean updatePwd(UserUpdatePwdVo userUpdatePwdVo) throws IOException {

        String userId = userUpdatePwdVo.getUserId();
        User user = userMapper.get(userId);
        if (user == null) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_LOGIN_PARAM_ERROR, null);
        }

        String pwd = userUpdatePwdVo.getPwd();
        if (!(EncryptUtil.encryptMD5(pwd).equals(user.getPwd()))) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_USER_PASSWROD_DISAFFINITY, null);
        }
        String newPwd = userUpdatePwdVo.getNewPwd();
        user.setPwd(EncryptUtil.encryptMD5(newPwd));
        userMapper.update(user);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean updateEmail(UserUpdateEmailVo userUpdateEmailVo) {

        String userId = userUpdateEmailVo.getUserId();
        User user = userMapper.get(userId);
        if (user == null) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_LOGIN_PARAM_ERROR, null);
        }

        String email = userUpdateEmailVo.getEmail();
        user.setEmail(email);
        userMapper.update(user);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean<UserDetailsPo> login(UserLoginVo loginUser) throws IOException {


        User user = UserUtils.toUser(loginUser);
        user = userMapper.getOne(user);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_LOGIN_USERNAME_PASSWROD_ERROR, null);
        }

        UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(user);
        userDetailsPo.setIconUrl(ossService.getUrl(OssConstant.OSS_USER_CENTERR_FFILEDIR,user.getIconOssResourceId()));
        return ResultUtil.formResult(true, ResultCode.SUCCESS, userDetailsPo);
    }
}
