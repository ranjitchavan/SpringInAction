package com.ranjit.bo;

import org.aspectj.lang.JoinPoint;

public class AfterThrows {
	public void after(JoinPoint jp) {
		System.out.println("AfterAdvice.after()");
		
	}
}
