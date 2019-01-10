package com.ranjit.bo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			ListEmpDAOImpl emp=context.getBean(ListEmpDAOImpl.class,"emp");
			System.out.println(emp.selectStudentById(1));
			System.out.println(emp.selectStudentById(2));
			System.out.println(emp.selectStudentById(3));
			System.out.println(emp.selectStudentById(4));
			System.out.println(emp.selectStudentById(5));
		}
	}
}
