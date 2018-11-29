package com.db.test.Testevent;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

public class BeanEventEnd implements ApplicationListener<ContextStoppedEvent> {
	long stime,entime;
	public void onApplicationEvent(ContextStoppedEvent event) {
		System.out.println("Application Stoped");
		}
		
		
	}


