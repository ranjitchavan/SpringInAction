package com.ranjit.bo;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
@Component("after")
public class AfterAdvice {
	public void after(JoinPoint jp) {
		System.out.println("AfterAdvice.after()");
		
	}
}
