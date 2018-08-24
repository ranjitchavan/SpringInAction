package com.spring.draw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.shape.Shape;

public class DrawShape {

		Shape shape;
	DrawShape(Shape shape){
		
		 this.shape=shape;
	}
	
	
	public void paint(){
		shape.draw();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    ApplicationContext context=new ClassPathXmlApplicationContext("draw.xml");
		DrawShape d=(DrawShape)context.getBean("drawShape");
		
		
		d.paint();
		
	}

}
