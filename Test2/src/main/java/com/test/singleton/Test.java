package com.test.singleton;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
public class Test {
	public static void main(String[] args) {
		XmlBeanFactory context= new XmlBeanFactory(new ClassPathResource("PP.xml") );
		//ClassPathXmlApplicationContext context1=new ClassPathXmlApplicationContext("PP.xml");		
			SingleBean sin=(SingleBean)context.getBean("single");
				
			System.out.println(sin.getAge());
	}
}
