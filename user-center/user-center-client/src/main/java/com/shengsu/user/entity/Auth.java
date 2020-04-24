package com.shengsu.user.entity;

import com.shengsu.user.po.UserDetailsPo;
import lombok.Data;

import java.io.Serializable;

@Data
public class Auth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5917228335207507063L;
	private String token;// token
	private UserDetailsPo user;

	public Auth() {
	}

	public Auth(String token, UserDetailsPo user) {
		this.token = token;
		this.user= user;
	}

}
