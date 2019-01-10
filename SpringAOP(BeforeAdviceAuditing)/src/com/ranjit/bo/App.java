package com.ranjit.bo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			try {
			ListEmpDAOImpl emp=(ListEmpDAOImpl)context.getBean("proxy");
			System.out.println(emp.getEmployeeNameAndSalaryById(-1));
			}catch(IllegalArgumentException e) {
				System.out.println("App.main()");
				}
		}
	}
}
