package com.ranjit.bo;

import java.lang.reflect.Method;


import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("before")
public class BeforeAdviceUserAuthentication implements MethodBeforeAdvice{
	ThreadLocal<UserBO> tLocal=new ThreadLocal<UserBO>();
	
	@Autowired(required=true)
	AuthenticationDAO dao;
	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println((UserBO)arg1[0]);
		if(isValid((UserBO)arg1[0])){
			System.out.println("successfully login");
		}else {
			throw new IllegalAccessError(" Access Error");
		}
	
	}public boolean isValid(UserBO u) {
		UserBO bean=tLocal.get();	
		if(bean!=null) {
			if(bean.getUsername().equals(u.getUsername())&&bean.getPassword().equals(u.getPassword())) {
				System.out.println(" round trip escape");
				return true;
				
			}return false;
		}else{
				if(dao.authenticateUser(u)){
					System.out.println(" round trip");
					tLocal.set(u);
					return true;
				}else 
					return false;
			}
	
		}
			
}
	

