package com.shengsu.user.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User,String> {


	User selectbyUserName(String userName);
	void updateRole(User user);
    List<User> listByUserType(String userType);
}
