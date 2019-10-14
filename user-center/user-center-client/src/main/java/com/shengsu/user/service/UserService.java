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

    void create(UserCreateVo userCreateVo) throws IOException, BizException;

    void edit(UserEditVo userEditVo) throws BizException, IOException;

    void updatePwd(UserUpdatePwdVo userUpdatePwdVo) throws IOException, BizException;



}
