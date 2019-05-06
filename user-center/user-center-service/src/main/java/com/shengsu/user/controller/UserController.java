package com.shengsu.user.controller;

import com.shengsu.user.entity.User;
import com.shengsu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value= "/user")
public class UserController {
	
	@Autowired
	UserService userService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@RequestMapping(value="/update")
	public void update(){
		User user = new User();
		user.setId(1);
		user.setUsername("zxh111223344");
		user.setAge(30);
		user.setCtm(new Date());
		userService.update(user);
	}
}
