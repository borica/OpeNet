package com.br.opet.entity;

import java.util.Date;

public class User {
	
	private String username;
	private String password;
	private Date lastLogin;
	
	public User(String username, String password, Date lastLogin) {
		this.username = username;
		this.password = password;
		this.lastLogin = lastLogin;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}
