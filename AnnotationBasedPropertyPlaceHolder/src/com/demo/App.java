package com.demo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
	try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
		System.out.println(context.getBean(Student.class));
			
		}
	}
	
}
