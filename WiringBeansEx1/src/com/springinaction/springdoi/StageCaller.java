package com.springinaction.springdoi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StageCaller {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ApplicationContext contex=new ClassPathXmlApplicationContext("stage.xml");
		
		
		Stage s1=(Stage)contex.getBean("stage");
		System.out.println(s1);
	}
}
