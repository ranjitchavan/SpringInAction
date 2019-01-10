package com.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages="com.demo")
public class BeanConfiguration {
	
	static {
		
		System.out.println("BeanConfiguration:static block{}");
	}
	public BeanConfiguration() {System.out.println("BeanConfiguration.BeanConfiguration() 0 param");}
	
	@Bean(name="student")
	@Scope("prototype")
	@DependsOn("wish")
	public Student  createStudent() {
		return new Student("Ranjit",22,"ADCET");
	}
}
