package com.ranjit.bo;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
@Component("afterReturn")
public class AfterReturningAdvice {
	public void afterReturn(JoinPoint jp,Object l) {
		System.out.println("AfterAdvice.after()"+l);
	}
}
