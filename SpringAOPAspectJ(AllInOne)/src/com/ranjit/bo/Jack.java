package com.ranjit.bo;

import org.springframework.stereotype.Component;

@Component("jack")
public class Jack {
	public Integer getJack() {
		System.out.println("Jack.getJack()");
		//throw new IllegalArgumentException("My life is just ok");
		return 1;
		
	}
}
