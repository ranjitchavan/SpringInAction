package com.ranjit.bo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ranjit.repo.StudentRegisterationDAO;

public class App {
	public static void main(String[] args) {
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
				StudentRegisterationDAO dao=context.getBean("studentDAO",StudentRegisterationDAO.class);
				System.out.println(dao.registerStudent(new StudentBO("Ranjit","Pass")));
		}
	}
}
