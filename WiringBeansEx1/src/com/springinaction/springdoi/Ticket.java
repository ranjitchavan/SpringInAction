package com.springinaction.springdoi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Ticket {

	public static void main(String[] args) {
	
		ApplicationContext context=new ClassPathXmlApplicationContext("ticket.xml");
		Ticket t=(Ticket)context.getBean("ticket");
		System.out.println(t);
		 t=(Ticket)context.getBean("ticket");
		System.out.println(t);
	}

}
