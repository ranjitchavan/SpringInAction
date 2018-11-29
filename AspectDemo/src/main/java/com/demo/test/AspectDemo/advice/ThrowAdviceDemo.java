package com.demo.test.AspectDemo.advice;

import org.springframework.aop.ThrowsAdvice;

public class ThrowAdviceDemo implements ThrowsAdvice{
	
	
	public void afterThrowing(RuntimeException ex) {
		
		System.out.println("Exception is Rised");
	}

}
