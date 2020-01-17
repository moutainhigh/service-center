package com.shengsu.any.user.entity;

import com.shengsu.any.user.po.UserDetailsPo;
import lombok.Data;

import java.io.Serializable;

@Data
public class Auth implements Serializable {
	private String token;// token
	private UserDetailsPo user;

	public Auth() {
	}

	public Auth(String token, UserDetailsPo user) {
		this.token = token;
		this.user= user;
	}

}
