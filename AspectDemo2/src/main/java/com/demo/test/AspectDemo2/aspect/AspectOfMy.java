package com.demo.test.AspectDemo2.aspect;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.expression.MethodFilter;



public class AspectOfMy implements ClassFilter,MethodMatcher{

	public boolean matches(Class clazz) {
		
		return false;
	}

	public boolean matches(Method method, Class<?> targetClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRuntime() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean matches(Method method, Class<?> targetClass, Object... args) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
