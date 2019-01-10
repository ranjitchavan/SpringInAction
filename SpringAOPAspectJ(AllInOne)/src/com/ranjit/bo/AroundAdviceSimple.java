package com.ranjit.bo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
@Component("around")
public class AroundAdviceSimple {
	
	public Object getProcessedJack(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("AroundAdviceSimple.getProcessedJack() start");;
		Object obj=pjp.proceed(pjp.getArgs());
		System.out.println("AroundAdviceSimple.getProcessedJack() end");
		return obj;
	}
	
	public Object getProcessedMick(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("AroundAdviceSimple.getProcessedMick() start");;
		Object obj=pjp.proceed(pjp.getArgs());
		System.out.println("AroundAdviceSimple.getProcessedMick() end");
		return obj;
	}
}
