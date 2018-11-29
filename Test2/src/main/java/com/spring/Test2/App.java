package com.spring.Test2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try(ClassPathXmlApplicationContext contex=new ClassPathXmlApplicationContext("cons.xml")){
        	
        	Cons co=contex.getBean(Cons.class);
        		System.out.println(co);
        }
    }
}
