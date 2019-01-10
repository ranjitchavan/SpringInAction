package com.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class Student {
	
	private String name;
	
	private int age;
	
	private String clg;
	
	public String getName() {
		return name;
	}
	public Student(String name, int age, String clg) {
		super();
		this.name = name;
		this.age = age;
		this.clg = clg;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getClg() {
		return clg;
	}
	public void setClg(String clg) {
		this.clg = clg;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", clg=" + clg + "]";
	}
	
	
}
