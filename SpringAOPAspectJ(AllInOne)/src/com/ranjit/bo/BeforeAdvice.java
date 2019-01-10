package com.ranjit.bo;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
@Component("before")
public class BeforeAdvice {
	public void before(JoinPoint jp) {
		System.out.println("BeforeAdvice.before()");
		
	}
}
