package com.ranjit.bo;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
@Component("before2")
public class BeforeAdvice2 {
	public void before(JoinPoint jp) {
		System.out.println("BeforeAdvice.before2()");
		
	}
}
