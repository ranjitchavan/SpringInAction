package com.spring.first;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       
    	
    	try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("test.xml")) {
    		
    		Employee e=context.getBean(Employee.class);
    		System.out.println(e);
    		
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}
    	
    }
}
