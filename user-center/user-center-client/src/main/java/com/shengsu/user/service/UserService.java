package com.shengsu.user.service;

import com.shengsu.app.constant.ResultBean;
import com.shengsu.app.exception.BizException;
import com.shengsu.base.service.BaseService;
import com.shengsu.user.entity.User;
import com.shengsu.user.po.UserDetailsPo;
import com.shengsu.user.vo.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface UserService extends BaseService<User,String> {

    ResultBean listPage(User user);

    ResultBean create(UserCreateVo userCreateVo) throws IOException;

    ResultBean edit(UserEditVo userEditVo) throws IOException;

    ResultBean updatePwd(UserUpdatePwdVo userUpdatePwdVo)throws IOException;

    ResultBean updateEmail(UserUpdateEmailVo userUpdateEmailVo);

    ResultBean login(UserLoginVo loginUser) throws IOException;

    ResultBean logout(String token);

    ResultBean getUserBytoken(String token);
}
