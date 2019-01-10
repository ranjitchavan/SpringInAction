package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")

public class AddStudent {
	@Autowired
	private Student str;

	public Student getStr() {
		return str;
	}

	public void setStr(Student str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return "AddStudent [str=" + str.hashCode() + "]";
	}
	
}
