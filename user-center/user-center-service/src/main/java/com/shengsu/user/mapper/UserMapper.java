package com.shengsu.user.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User,String> {
	public User getUserById(Integer id);
	public  void save1();
	public void save2();
}
