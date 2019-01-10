package com.ranjit.bo;

import org.springframework.stereotype.Component;

@Component
public class StudentBO {
	private String password; 
	private String name;
	
	public StudentBO(String password, String name) {
		super();
		this.password = password;
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "StudentBO [password=" + password + ", name=" + name + "]";
	}
	public StudentBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
