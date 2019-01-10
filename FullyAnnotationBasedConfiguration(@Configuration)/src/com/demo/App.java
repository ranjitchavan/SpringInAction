package com.demo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
	public static void main(String[] args) {
	try(AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(BeanConfiguration.class)){
		
		System.out.println(context.getBean(AddStudent.class));	
		System.out.println(context.getBean(AddStudent.class));
		}
	}
	
}
