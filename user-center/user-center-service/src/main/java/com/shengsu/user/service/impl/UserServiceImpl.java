package com.shengsu.user.service.impl;

import com.shengsu.app.constant.ResultCode;
import com.shengsu.app.exception.BizException;
import com.shengsu.app.util.OssClientUtil;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.user.entity.User;
import com.shengsu.user.mapper.UserMapper;
import com.shengsu.user.service.UserService;
import com.shengsu.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User,String> implements UserService {
	@Autowired
	UserMapper userMapper;

	@Autowired
	OssClientUtil ossClientUtil ;

	//0SS文件路径
	private final static String fileDir = "user-center";

	@Override
	public BaseMapper<User, String> getBaseMapper() {
		return userMapper;
	}

	@Override
	public int userExistCheck(User user) {
		return userMapper.userExistCheck(user);
	}

	@Override
	public void create(User user) throws IOException {
		user.setUserId(UUID.randomUUID().toString());
		user.setPwd(EncryptUtil.encryptMD5(user.getPwd()));
		save(user);
	}

	/**
	 * 组装单个用户的头像URL
	 * @param user
	 */
	@Override
	public void assembleUserIconImageUrl(User user) {
		if (user == null)
			return;
		user.setIconImageUrl(ossClientUtil.getUrl(fileDir,user.getIconOssResourceId()));
	}

	@Override
	public void updatePwd(User user) throws IOException, BizException {
		if (null==user){
			return;
		}
		User usr = userMapper.get(user.getUserId());
		if (!usr.getPwd().equals(user.getPwd())){
			throw new BizException(ResultCode.EXCEPTION_USER_PASSWROD_DISAFFINITY);
		}
		usr.setPwd(EncryptUtil.encryptMD5(user.getNewPwd()));
		userMapper.update(usr);
	}

	@Override
	public void updateUser(User user) throws BizException {
		int count=userMapper.userExistCheckExceptSelf(user);
		if (count>0) {
			throw new BizException(ResultCode.EXCEPTION_REGISTER_USER_EXISTED);
		}
		update(user);
	}
}
