package com.spl.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			Student s=context.getBean(Student.class);
			System.out.println(s.getNullEr());
			
			
		}
	}
}
