package com.shengsu.user.service.impl;

import com.shengsu.app.constant.ResultBean;
import com.shengsu.app.constant.ResultCode;
import com.shengsu.app.exception.BizException;
import com.shengsu.app.util.OssClientUtil;
import com.shengsu.app.util.ResultUtil;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.user.entity.User;
import com.shengsu.user.mapper.UserMapper;
import com.shengsu.user.po.UserDetailsPo;
import com.shengsu.user.service.UserService;
import com.shengsu.user.util.UserUtils;
import com.shengsu.user.vo.UserCreateVo;
import com.shengsu.user.vo.UserEditVo;
import com.shengsu.user.vo.UserUpdatePwdVo;
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
    UserMapper userMapper;

    @Autowired
    OssClientUtil ossClientUtil;

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
    public void create(UserCreateVo userCreateVo) throws IOException, BizException {
        String userName = userCreateVo.getUserName();
        User user = userMapper.selectbyUserName(userName);
        if (user != null) {
            throw new BizException(ResultCode.EXCEPTION_REGISTER_USER_EXISTED);
        }
        user = UserUtils.toUser(userCreateVo);
        userMapper.save(user);
    }

    @Override
    public void edit(UserEditVo userEditVo) throws BizException, IOException {

        String userName = userEditVo.getUserName();
        User user = userMapper.selectbyUserName(userName);
        if (user != null && !user.getUserId().equals(userEditVo.getUserId())) {
            throw new BizException(ResultCode.EXCEPTION_REGISTER_USER_EXISTED);
        }

        user = UserUtils.toUser(userEditVo);
        userMapper.update(user);
    }

    @Override
    public void updatePwd(UserUpdatePwdVo userUpdatePwdVo) throws IOException, BizException {
        String userId = userUpdatePwdVo.getUserId();
        String pwd = EncryptUtil.encryptMD5(userUpdatePwdVo.getPwd());
        User user = userMapper.get(userId);
        if (!user.getPwd().equals(user.getPwd())) {
            throw new BizException(ResultCode.EXCEPTION_USER_PASSWROD_DISAFFINITY);
        }
        user.setPwd(pwd);
        userMapper.update(user);
    }


}
