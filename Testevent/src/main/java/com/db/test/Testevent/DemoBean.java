package com.db.test.Testevent;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class DemoBean implements InitializingBean,DisposableBean{
	String name;
	int age;
	public String getName() {
		return name;
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
	
	
	public void myInit() {
		System.out.println("initalization phase");
	}
	@Override
	public void destroy() {
		System.out.println("Destroy method");
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Initalization");
		
	}
}
