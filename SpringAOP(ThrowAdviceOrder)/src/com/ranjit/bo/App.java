package com.ranjit.bo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) throws Exception{
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			Flipkart flip=context.getBean("proxy",Flipkart.class);
			System.out.println(flip.purchaseProduct(0));
		}
	}
}
