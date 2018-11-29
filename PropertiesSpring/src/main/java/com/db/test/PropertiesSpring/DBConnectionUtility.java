package com.db.test.PropertiesSpring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DBConnectionUtility {
		public static void main(String a[]) {
			try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("dbconfig.xml")){
				
				DBConnectionImpl db=context.getBean("db",DBConnectionImpl.class);
				System.out.println(db.getConnection());
				
			}
			
		}
}
