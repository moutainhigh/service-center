package com.shengsu.user.service;

import com.shengsu.app.constant.ResultBean;
import com.shengsu.app.exception.BizException;
import com.shengsu.base.service.BaseService;
import com.shengsu.user.entity.User;
import com.shengsu.user.vo.UserCreateVo;
import com.shengsu.user.vo.UserEditVo;
import com.shengsu.user.vo.UserUpdatePwdVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface UserService extends BaseService<User,String> {

    ResultBean listPage(User user);

    ResultBean create(UserCreateVo userCreateVo) throws IOException;

    ResultBean edit(UserEditVo userEditVo) throws IOException;

    ResultBean updatePwd(UserUpdatePwdVo userUpdatePwdVo)throws IOException;


}
