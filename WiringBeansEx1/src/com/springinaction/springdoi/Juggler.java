package com.springinaction.springdoi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Juggler implements Performer {
	private int beanBags=3;
	
	 public Juggler() {
		// TODO Auto-generated constructor stub
	}
	 
	 public Juggler(int bean) {
		 this.beanBags=bean;
	 }
	 
	@Override
	public void perform() {
		// TODO Auto-generated method stub
		System.out.println("JUGGLING  "+beanBags+" BEAN BAGS");
	}
	
	public static void main(String ap[]) {
		
		
			ApplicationContext context=new ClassPathXmlApplicationContext("juggler.xml");
			Juggler j=(Juggler)context.getBean("first");
			j.perform();
			System.out.println(j);
			 j=(Juggler)context.getBean("first");
			j.perform();
			System.out.println(j);
			j=(Juggler)context.getBean("second");
			j.perform();
			System.out.println(j);
	}

}
