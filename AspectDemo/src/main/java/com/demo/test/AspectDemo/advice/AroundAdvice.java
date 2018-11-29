package com.demo.test.AspectDemo.advice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;




public class AroundAdvice implements MethodInterceptor
{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object obj=(Object)invocation.getThis();
		Object ret=null;
		System.out.println(
			
			"\t Before Calling "+(Method)invocation.getMethod()+
			"\tWith Argument"+(Object[])invocation.getArguments()+
			"\tObject Targer"+obj
			);			
			try {
		
				ret=invocation.proceed();
			}catch(Exception e) {
				
				ret=null;
			}
		System.out.println(
				
				"\tAfter  Calling "+(Method)invocation.getMethod()+
				"\tWith Argument"+(Object[])invocation.getArguments()+
				"\tObject Targer"+obj
				);			
			
		return "Jack";
	}
	
}
