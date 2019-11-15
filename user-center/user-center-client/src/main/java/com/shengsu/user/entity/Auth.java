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
	private long iat;// 创建时间
	private long exp;// 过期时间
	private String token;// token
	private UserDetailsPo user;

	public Auth() {
	}

	public Auth(long iat, long exp, String token, UserDetailsPo user) {
		this.iat = iat;
		this.exp = exp;
		this.token = token;
		this.user= user;
	}

}
