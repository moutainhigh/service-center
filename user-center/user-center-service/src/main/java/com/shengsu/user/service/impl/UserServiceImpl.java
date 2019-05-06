package com.shengsu.user.service.impl;

import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.user.entity.User;
import com.shengsu.user.mapper.UserMapper;
import com.shengsu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User,String>  implements UserService  {
	UserMapper userMapper;
	
	@Autowired
	public void setUserMapper(UserMapper userMapper){
		this.userMapper = userMapper;
		this.baseMapper = userMapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void transTest(User user) {
        System.out.println(user);
        userMapper.save1();
		userMapper.save2();
	}
}
