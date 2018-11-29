package com.db.test.Testevent;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
    		
    	BussnessObject1 bo=context.getBean("bo1",BussnessObject1.class);
    	System.out.println(bo.getObject().hashCode());
    	System.out.println(bo.getObject().hashCode());
    	System.out.println(bo.getObject().hashCode());
    	}
    }
}
