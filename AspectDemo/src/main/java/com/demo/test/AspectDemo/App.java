package com.demo.test.AspectDemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 */

@Component("app")
public class App 
{
    public static void main( String[] args )
    {
     try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("test.xml")){
    	CustomerService service=(CustomerService)context.getBean("csProxy");
    	service.printException();
     }
    }
}
