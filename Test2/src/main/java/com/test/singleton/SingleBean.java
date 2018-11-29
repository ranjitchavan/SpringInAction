package com.test.singleton;

public class SingleBean {
	public SingleBean(){
		System.out.println("SingleTOn");
		
	}
	static int age;
	private String name;
	
	public static int getAge() {
		return age;
	}
	public static void setAge(int age) {
		SingleBean.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}