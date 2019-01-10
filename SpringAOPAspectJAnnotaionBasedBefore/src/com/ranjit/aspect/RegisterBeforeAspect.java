package com.ranjit.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class RegisterBeforeAspect {
	@Pointcut("execution(* com.ranjit.repo.StudentRegisterationDAO.*(*)) within(com.ranjit.bo.*)")
	public void doThing() {
		
	}
	@Before("doThing()")
	public void beforeRegister(JoinPoint jp) {
		System.out.println("RegisterBeforeAspect.beforeRegister()");
		System.out.println();
	}
}
