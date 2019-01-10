package com.ranjit.bo;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
@Component("throwingAspect")
public class AfterThrowingIllegalArgument {
	public void throwMe(JoinPoint jp,RuntimeException ae) {
		System.out.println("AfterThrowingIllegalArgument.throwMe()"+ae.getMessage());
		
		
	}
}
