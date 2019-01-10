package com.ranjit.bo;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;
@Component("after")
public class DiscountAfterThrows implements ThrowsAdvice{


	public void afterReturning(Object retVal, Method m1, Object[] arg2, Object arg3) throws Throwable {
		// TODO Auto-generated method stub
		if(Double.valueOf(String.valueOf(retVal))<450)
			throw new IllegalArgumentException("amount should be greater than 450");
		else if(Double.valueOf(String.valueOf(retVal))<1000) {
			System.out.println("Discount is 1 %"+retVal);
		}else if(Double.valueOf(String.valueOf(retVal))<5000) {
			System.out.println("Discount is 5 %"+retVal);
		}else if(Double.valueOf(String.valueOf(retVal))<10000) {
			System.out.println("Discount is 10 %"+retVal);
		}else if(Double.valueOf(String.valueOf(retVal))<20000) {
			System.out.println("Discount is 20 %"+retVal);
		}else if(Double.valueOf(String.valueOf(retVal))<35000) {
			System.out.println("Discount is 35 %"+retVal);
		}else
			System.out.println("Discount is 50 %"+retVal);
		
		
		retVal=0;
		return;
	}
	
	public void afterThrowing(IllegalArgumentException e) {
		
		System.out.println("DiscountAfterThrows.afterThrowing() : IllegalArgumentException");
	}
	/*public void afterThrowing(RuntimeException e) {
		
		System.out.println("DiscountAfterThrows.afterThrowing() : RuntimeException");
	}*/
    public void afterThrowing(Throwable e){
		
		System.out.println("DiscountAfterThrows.afterThrowing() : Throwable");
	}
    public void afterThrowing(Method m1, Object[] arg2, Object arg3,Throwable e){
		
		System.out.println("DiscountAfterThrows.afterThrowing() : Throwable with all args");
	}
}
