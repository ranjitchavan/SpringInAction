package com.ranjit.bo;

import java.lang.reflect.Method;

import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;
@Component("before")
public class BeforeAdviceEmployee implements MethodBeforeAdvice{
	
	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("BeforeAdviceEmployee.before() start");
		int num=Integer.parseInt(String.valueOf(arg1[0]));
		
		if(num<1)
			throw new  IllegalArgumentException("Employee Id Invalid");
		System.out.println("BeforeAdviceEmployee.before() end");
	}
	
}
