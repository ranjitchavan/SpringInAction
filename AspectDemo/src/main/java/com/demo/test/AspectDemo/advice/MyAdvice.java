package com.demo.test.AspectDemo.advice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;




public class MyAdvice implements MethodBeforeAdvice,AfterReturningAdvice{

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		
		System.out.println("Before Calling :"+method.getName()+" with arguments"+args+" On :"+target);
	
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		//returnValue="raj";
		System.out.printf("\n After Return :%s  Method Name :%s  Object argument length :%d  Runtime Object %s ",
					returnValue,method.getName(),args.length,target.getClass().getName()
		);
	}
	
	
}
