package com.einfochips.webportal.dto;

public class LoginResponse {

	private String token;
	private boolean newUser;

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
