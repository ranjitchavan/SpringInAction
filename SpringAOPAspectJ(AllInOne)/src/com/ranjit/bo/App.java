package com.ranjit.bo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
				/*Mick jack=context.getBean("mick",Mick.class);
				System.out.println(jack.getMick());
				*/
				Jack jack=context.getBean(Jack.class);
				System.out.println(jack.getJack());
		}
	}
}
