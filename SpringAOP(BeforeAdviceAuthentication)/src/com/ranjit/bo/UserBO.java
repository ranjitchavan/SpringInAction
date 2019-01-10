package com.ranjit.bo;

import org.springframework.stereotype.Component;

@Component
public class UserBO {
	private String username;
	private String password;
	private String student_class;
	
	public String getClass_name() {
		return student_class;
	}
	public void setClass_name(String class_name) {
		this.student_class = class_name;
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
	public UserBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserBO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserBO [username=" + username + ", password=" + password + ", student_class=" + student_class + "]";
	}
	
}
