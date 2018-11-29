package com.spring.Test2;

public class Cons implements ConsIMP{

	private String name;
	private int age;
	
	
	public Cons(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}


	@Override
	public void sayHello() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String toString() {
		return "Cons [name=" + name + ", age=" + age + "]";
	}

}
