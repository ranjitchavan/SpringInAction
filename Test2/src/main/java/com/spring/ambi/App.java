package com.spring.ambi;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ClassPathXmlApplicationContext cont=new ClassPathXmlApplicationContext("P.xml")){
			System.out.println(cont.getBean("jObject").hashCode());
			try(ClassPathXmlApplicationContext cont1=new ClassPathXmlApplicationContext(new String[] {"C.xml"},cont)){
				
				System.out.println(cont1.getBean("c").hashCode());
				
			}
		}
	}

}
