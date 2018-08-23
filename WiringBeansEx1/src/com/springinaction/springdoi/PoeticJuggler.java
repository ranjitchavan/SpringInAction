package com.springinaction.springdoi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PoeticJuggler extends Juggler {

	Poem poem;
	public PoeticJuggler(Poem p) {
		
		this.poem=p;
		
	}
	
	public PoeticJuggler(int bags,Poem p) {
		
		super(bags);
		this.poem=p;
	}
	public void perform() {
		super.perform();
		System.out.println("while recting.....");
		poem.recite();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ApplicationContext context=new ClassPathXmlApplicationContext("poetic.xml");
		
		
		PoeticJuggler po=(PoeticJuggler)context.getBean("poetic1");
		po.perform();
		 po=(PoeticJuggler)context.getBean("poetic2");
		po.perform();
		
	}

}
