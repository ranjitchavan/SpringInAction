package com.ranjit.bo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
				AuthenticationDAO dao=context.getBean("proxy",AuthenticationDAO.class);
				Thread t1=new Thread(()-> {
					
					System.out.println(dao.getClassNameStudent(new UserBO("ranjit","raj")));
						
				});
				Thread t2=new Thread(()-> {
					
					System.out.println(dao.getClassNameStudent(new UserBO("ranjit","raj")));
						
				});
				
				t1.start();
				t2.start();
		}
	}
}
